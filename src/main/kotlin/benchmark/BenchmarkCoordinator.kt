package benchmark

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import java.nio.file.Files
import java.nio.file.Path
import java.util.Comparator
import java.util.LinkedHashMap

class BenchmarkCoordinator(
    private val launcher: BenchmarkProcessLauncher,
    commonArguments: List<String>,
    private val debug: Boolean = false,
    private val compilerIdentity: String = "unknown",
    private val grillIdentity: String = "unknown"
) {
    private val commonArguments = commonArguments.toList()
    private val mapper = JsonMapper.builder().build()

    fun run(request: BenchmarkRequest): BenchmarkReport {
        val temporaryRoot = Files.createTempDirectory("grill-benchmark-")
        try {
            val discoveryOutput = temporaryRoot.resolve("discovery.json")
            val filterArguments = request.filter
                ?.takeIf { it.isNotBlank() }
                ?.let { arrayOf("-benchmarkFilter", it) }
                ?: emptyArray()
            val discoveryArguments = workerArguments(
                "-runbenchmarks",
                "-benchmarkList",
                *filterArguments,
                "-benchmarkOutput",
                discoveryOutput.toString()
            )
            val selected = launchAndRead(discoveryArguments, discoveryOutput, "discovery", ::parseDiscovery).value

            val forksByBenchmark = LinkedHashMap<String, MutableList<BenchmarkFork>>()
            val checksums = mutableMapOf<String, Int>()
            var executionIndex = 0
            repeat(request.forks) { forkRound ->
                val roundNames = if (forkRound % 2 == 0) selected else selected.asReversed()
                roundNames.forEach { qualifiedName ->
                    val output = temporaryRoot.resolve("execution-$executionIndex-$forkRound-${safeFileName(qualifiedName)}.json")
                    executionIndex++
                    val arguments = workerArguments(
                        "-runbenchmarks",
                        "-benchmarkName",
                        qualifiedName,
                        "-benchmarkWarmup",
                        request.warmup.toString(),
                        "-benchmarkIterations",
                        request.iterations.toString(),
                        "-benchmarkOutput",
                        output.toString()
                    )
                    val parsedExecution = launchAndRead(arguments, output, "execution for $qualifiedName") {
                        parseExecution(it, qualifiedName, request.iterations)
                    }
                    val result = parsedExecution.value
                    val previousChecksum = checksums.putIfAbsent(qualifiedName, result.checksum)
                    if (previousChecksum != null && previousChecksum != result.checksum) {
                        if (!parsedExecution.diagnosticsEmitted) {
                            parsedExecution.diagnostics.forEach(System.err::println)
                        }
                        check(false) { "benchmark checksum changed across forks for $qualifiedName" }
                    }
                    val forks = forksByBenchmark.getOrPut(qualifiedName) { mutableListOf() }
                    forks += BenchmarkFork(forkRound + 1, result.batchSize, result.samplesNanos)
                }
            }

            val aggregates = selected.map { qualifiedName ->
                val forks = forksByBenchmark[qualifiedName].orEmpty().toList()
                check(forks.size == request.forks) {
                    "benchmark $qualifiedName did not produce ${request.forks} fork results"
                }
                val allSamples = forks.flatMap { it.samplesNanos }
                BenchmarkAggregate(
                    qualifiedName = qualifiedName,
                    checksum = checksums.getValue(qualifiedName),
                    forks = forks,
                    statistics = BenchmarkStatistics.fromSamples(allSamples)
                )
            }
            if (aggregates.size == 2) {
                check(aggregates[0].checksum == aggregates[1].checksum) {
                    "benchmark checksums differ: ${aggregates[0].qualifiedName}=${aggregates[0].checksum}, " +
                        "${aggregates[1].qualifiedName}=${aggregates[1].checksum}"
                }
            }
            return BenchmarkReport(
                environment = BenchmarkEnvironment(
                    os = System.getProperty("os.name", "unknown"),
                    jvm = System.getProperty("java.version", "unknown"),
                    compiler = compilerIdentity,
                    grill = grillIdentity,
                    cpuCount = Runtime.getRuntime().availableProcessors()
                ),
                filter = request.filter,
                forks = request.forks,
                warmup = request.warmup,
                iterations = request.iterations,
                benchmarks = aggregates
            )
        } finally {
            deleteTree(temporaryRoot)
        }
    }

    fun run(filter: String?, forks: Int, warmup: Int, iterations: Int): BenchmarkReport {
        return run(BenchmarkRequest(filter, forks, warmup, iterations))
    }

    private fun workerArguments(vararg extra: String): List<String> {
        val arguments = commonArguments.toMutableList()
        if (!arguments.contains("-compactOutput")) {
            arguments += "-compactOutput"
        }
        arguments += extra
        return arguments
    }

    private fun <T> launchAndRead(
        arguments: List<String>,
        output: Path,
        phase: String,
        parse: (JsonNode) -> T
    ): ParsedWorker<T> {
        val result = launcher.run(arguments)
        var diagnosticsEmitted = false
        fun emitDiagnostics() {
            if (!diagnosticsEmitted) {
                result.output.forEach(System.err::println)
                diagnosticsEmitted = true
            }
        }
        if (debug) {
            emitDiagnostics()
        }
        if (result.exitCode != 0) {
            emitDiagnostics()
        }
        check(result.exitCode == 0) { "$phase worker exited with code ${result.exitCode}" }
        if (!Files.isRegularFile(output)) {
            emitDiagnostics()
            check(false) { "$phase worker did not write ${output.fileName}" }
        }
        val document = try {
            mapper.factory.createParser(Files.readString(output)).use { parser ->
                val parsed = mapper.readTree<JsonNode>(parser)
                check(parser.nextToken() == null) { "$phase worker wrote trailing JSON content" }
                parsed
            }
        } catch (exception: Exception) {
            emitDiagnostics()
            throw IllegalStateException("$phase worker wrote malformed JSON", exception)
        }
        if (document == null || !document.isObject) {
            emitDiagnostics()
            check(false) { "$phase worker JSON must be an object" }
        }
        return try {
            ParsedWorker(parse(document), result.output.toList(), diagnosticsEmitted)
        } catch (exception: Exception) {
            emitDiagnostics()
            throw exception
        }
    }

    private fun parseDiscovery(document: JsonNode): List<String> {
        requireExactFields(document, setOf("schema", "mode", "benchmarks"), "discovery")
        check(document.requiredText("schema") == BENCHMARK_WORKER_SCHEMA) { "discovery worker schema is invalid" }
        check(document.requiredText("mode") == "discovery") { "discovery worker mode is invalid" }
        val benchmarks = document["benchmarks"]
        check(benchmarks.isArray) { "discovery worker benchmarks must be an array" }
        val names = benchmarks.map {
            check(it.isTextual && it.textValue().isNotBlank()) { "discovery benchmark name must be non-empty text" }
            it.textValue()
        }
        check(names.size == names.distinct().size) { "discovery returned duplicate benchmark names" }
        check(names.isNotEmpty()) { "benchmark discovery selected no benchmarks" }
        return names
    }

    private fun parseExecution(document: JsonNode, expectedName: String, iterations: Int): WorkerExecution {
        requireExactFields(
            document,
            setOf("schema", "mode", "qualifiedName", "checksum", "batchSize", "samplesNanos"),
            "execution"
        )
        check(document.requiredText("schema") == BENCHMARK_WORKER_SCHEMA) { "execution worker schema is invalid" }
        check(document.requiredText("mode") == "execution") { "execution worker mode is invalid" }
        check(document.requiredText("qualifiedName") == expectedName) {
            "execution worker name does not match requested benchmark $expectedName"
        }
        val checksum = document["checksum"]
        check(checksum.isIntegralNumber && checksum.canConvertToInt()) { "execution checksum must be an integer" }
        val batchSize = document["batchSize"]
        check(batchSize.isIntegralNumber && batchSize.canConvertToInt() && batchSize.intValue() > 0) {
            "execution batchSize must be positive"
        }
        val samplesNode = document["samplesNanos"]
        check(samplesNode.isArray && samplesNode.size() == iterations) {
            "execution samplesNanos must contain exactly $iterations samples"
        }
        val samples = samplesNode.map {
            check(it.isIntegralNumber && it.canConvertToLong() && it.longValue() >= 0) {
                "execution samplesNanos must contain non-negative integers"
            }
            it.longValue()
        }
        return WorkerExecution(checksum.intValue(), batchSize.intValue(), samples)
    }

    private fun requireExactFields(document: JsonNode, fields: Set<String>, label: String) {
        val actual = document.fieldNames().asSequence().toSet()
        check(actual == fields) { "$label worker JSON fields are invalid: expected $fields, got $actual" }
    }

    private fun JsonNode.requiredText(field: String): String {
        val value = this[field]
        check(value != null && value.isTextual) { "worker field $field must be text" }
        return value.textValue()
    }

    private fun safeFileName(name: String): String = name.replace(Regex("[^A-Za-z0-9_.-]"), "_")

    private fun deleteTree(path: Path) {
        if (!Files.exists(path)) return
        Files.walk(path).use { stream ->
            stream.sorted(Comparator.reverseOrder()).forEach { Files.deleteIfExists(it) }
        }
    }

    private data class WorkerExecution(
        val checksum: Int,
        val batchSize: Int,
        val samplesNanos: List<Long>
    )

    private data class ParsedWorker<T>(
        val value: T,
        val diagnostics: List<String>,
        val diagnosticsEmitted: Boolean
    )
}
