import benchmark.BenchmarkCoordinator
import benchmark.BenchmarkProcessLauncher
import benchmark.BenchmarkProcessResult
import benchmark.BenchmarkRequest
import benchmark.BenchmarkRenderer
import com.fasterxml.jackson.databind.ObjectMapper
import file.ExitHandler
import file.SetupApp
import file.SetupMain
import org.testng.Assert
import org.testng.annotations.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.Comparator

private class CoordinatorExitException(val code: Int) : RuntimeException("exit $code")

private class FixtureLauncher(
    private val names: List<String> = listOf("Bench.Fast", "Bench.Slow"),
    private val samples: Map<String, List<Long>> = mapOf(
        "Bench.Fast" to listOf(100L, 120L),
        "Bench.Slow" to listOf(900L, 1_000L)
    ),
    private val checksums: Map<String, Int> = names.associateWith { 4950 },
    private val outputFor: (List<String>, Int) -> String? = { arguments, _ ->
        val name = arguments.optionValue("-benchmarkName")
        if (name == null) {
            val benchmarkJson = names.joinToString(",") { "\"$it\"" }
            """{"schema":"wurst-benchmark-worker-v2","mode":"discovery","benchmarks":[$benchmarkJson]}"""
        } else {
            val values = samples.getValue(name)
            workerExecutionJson(name, checksums.getValue(name), values, batchSize = 2)
        }
    },
    private val diagnosticsFor: (List<String>, Int) -> List<String> = { _, _ ->
        listOf("worker log must stay captured")
    }
) : BenchmarkProcessLauncher {
    val calls = mutableListOf<List<String>>()
    val outputPaths = mutableListOf<Path>()
    var active = 0
    var maxActive = 0
    var invocation = 0
    var exitCode = 0

    override fun run(arguments: List<String>): BenchmarkProcessResult {
        Assert.assertEquals(active, 0, "worker launches must be serial")
        active++
        maxActive = maxOf(maxActive, active)
        calls += arguments
        val output = Path.of(arguments.optionValue("-benchmarkOutput")!!)
        outputPaths.add(output)
        val callIndex = invocation++
        val json = outputFor(arguments, callIndex)
        if (json != null) {
            Files.writeString(output, json)
        }
        active--
        return BenchmarkProcessResult(exitCode, diagnosticsFor(arguments, callIndex))
    }
}

private fun workerExecutionJson(
    name: String,
    checksum: Int = 1,
    samples: List<Long> = listOf(1L),
    batchSize: Int = 1
): String =
    """{"schema":"wurst-benchmark-worker-v2","mode":"execution","qualifiedName":"$name","checksum":$checksum,"batchSize":$batchSize,"samplesNanos":[${samples.joinToString(",")}]""" + "}"

private fun List<String>.optionValue(option: String): String? {
    val index = indexOf(option)
    return if (index >= 0 && index + 1 < size) this[index + 1] else null
}

private fun expectCoordinatorFailure(block: () -> Unit) {
    try {
        block()
        Assert.fail("expected benchmark coordination to fail")
    } catch (_: IllegalStateException) {
    }
}

private fun deleteTree(path: Path) {
    if (Files.exists(path)) {
        Files.walk(path).use { stream ->
            stream.sorted(Comparator.reverseOrder()).forEach(Files::deleteIfExists)
        }
    }
}

private fun captureOutput(block: () -> Unit): Pair<String, String> {
    val oldOut = System.out
    val oldErr = System.err
    val out = ByteArrayOutputStream()
    val err = ByteArrayOutputStream()
    System.setOut(PrintStream(out, true, StandardCharsets.UTF_8))
    System.setErr(PrintStream(err, true, StandardCharsets.UTF_8))
    try {
        block()
    } finally {
        System.setOut(oldOut)
        System.setErr(oldErr)
    }
    return out.toString(StandardCharsets.UTF_8) to err.toString(StandardCharsets.UTF_8)
}

class BenchmarkCoordinatorTests {

    @Test
    fun realBenchmarkWorkerProcessCapturesOutputAndPreservesExitCodes() {
        val processDirectory = Files.createTempDirectory("grill-real-worker-process")
        val javaExecutable = Path.of(
            System.getProperty("java.home"),
            "bin",
            if (System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) "java.exe" else "java"
        ).toString()
        lateinit var success: BenchmarkProcessResult
        lateinit var failure: BenchmarkProcessResult

        try {
            val (stdout, stderr) = captureOutput {
                success = SetupApp.runBenchmarkWorkerProcess(
                    listOf(javaExecutable, "-version"),
                    processDirectory
                )
                failure = SetupApp.runBenchmarkWorkerProcess(
                    listOf(javaExecutable, "-definitely-not-a-real-java-option"),
                    processDirectory
                )
            }

            Assert.assertEquals(success.exitCode, 0)
            Assert.assertTrue(success.output.isNotEmpty())
            Assert.assertNotEquals(failure.exitCode, 0)
            Assert.assertTrue(failure.output.isNotEmpty())
            Assert.assertEquals(stdout, "")
            Assert.assertEquals(stderr, "")
        } finally {
            deleteTree(processDirectory)
        }
    }

    @Test
    fun launchesDiscoveryThenSerialAlternatingWorkerRounds() {
        val launcher = FixtureLauncher()
        val report = BenchmarkCoordinator(launcher, listOf("java", "-jar", "compiler.jar"))
            .run(BenchmarkRequest("Bench", forks = 2, warmup = 3, iterations = 2))

        Assert.assertEquals(launcher.calls.size, 5)
        Assert.assertNull(launcher.calls[0].optionValue("-benchmarkName"))
        Assert.assertEquals(
            launcher.calls.drop(1).mapNotNull { it.optionValue("-benchmarkName") },
            listOf("Bench.Fast", "Bench.Slow", "Bench.Slow", "Bench.Fast")
        )
        val outputArguments = launcher.calls.map { it.optionValue("-benchmarkOutput")!! }
        Assert.assertEquals(outputArguments.distinct().size, outputArguments.size, "every worker needs a unique output path")
        Assert.assertEquals(launcher.maxActive, 1)
        Assert.assertTrue(launcher.calls[0].containsAll(listOf("-compactOutput", "-runbenchmarks", "-benchmarkList", "-benchmarkFilter", "Bench")))
        Assert.assertTrue(launcher.calls.drop(1).all { args ->
            args.containsAll(listOf("-compactOutput", "-runbenchmarks", "-benchmarkWarmup", "3", "-benchmarkIterations", "2"))
        })
        Assert.assertEquals(report.benchmarks.map { it.qualifiedName }, listOf("Bench.Fast", "Bench.Slow"))
        Assert.assertTrue(launcher.outputPaths.all { !Files.exists(it) })
        Assert.assertEquals(report.benchmarks.first().forks.size, 2)
    }

    @Test
    fun preservesRawSamplesAndBatchSizesAndRecomputesNearestRankAggregates() {
        val launcher = FixtureLauncher(
            names = listOf("Bench.Only"),
            samples = mapOf("Bench.Only" to listOf(100L, 500L)),
            checksums = mapOf("Bench.Only" to 42)
        )
        val report = BenchmarkCoordinator(launcher, emptyList())
            .run(BenchmarkRequest(null, forks = 3, warmup = 0, iterations = 2))
        val benchmark = report.benchmarks.single()

        Assert.assertEquals(benchmark.checksum, 42)
        Assert.assertEquals(benchmark.forks.flatMap { it.samplesNanos }, listOf(100L, 500L, 100L, 500L, 100L, 500L))
        Assert.assertTrue(benchmark.forks.all { it.batchSize == 2 })
        Assert.assertEquals(benchmark.statistics.median, 100L)
        Assert.assertEquals(benchmark.statistics.p95, 500L)
        Assert.assertEquals(benchmark.statistics.min, 100L)
        Assert.assertEquals(benchmark.statistics.max, 500L)
    }

    @Test
    fun acceptsSamplesOnlyExecutionDocumentsAndDerivesStatistics() {
        val launcher = FixtureLauncher(
            names = listOf("Bench.Only"),
            outputFor = { arguments, _ ->
                val name = arguments.optionValue("-benchmarkName")
                if (name == null) {
                    """{"schema":"wurst-benchmark-worker-v2","mode":"discovery","benchmarks":["Bench.Only"]}"""
                } else {
                    """{"schema":"wurst-benchmark-worker-v2","mode":"execution","qualifiedName":"Bench.Only","checksum":42,"batchSize":1,"samplesNanos":[100,500]}"""
                }
            }
        )

        val report = BenchmarkCoordinator(launcher, emptyList())
            .run(BenchmarkRequest(null, forks = 1, warmup = 0, iterations = 2))
        val statistics = report.benchmarks.single().statistics

        Assert.assertEquals(statistics.median, 100L)
        Assert.assertEquals(statistics.p95, 500L)
    }

    @Test
    fun acceptsZeroNanosecondWorkerSamples() {
        val launcher = FixtureLauncher(
            names = listOf("Bench.Only"),
            outputFor = { arguments, _ ->
                val name = arguments.optionValue("-benchmarkName")
                if (name == null) {
                    """{"schema":"wurst-benchmark-worker-v2","mode":"discovery","benchmarks":["Bench.Only"]}"""
                } else {
                    workerExecutionJson(name, samples = listOf(0L))
                }
            }
        )

        val report = BenchmarkCoordinator(launcher, emptyList())
            .run(BenchmarkRequest(null, forks = 1, warmup = 0, iterations = 1))

        Assert.assertEquals(report.benchmarks.single().forks.single().samplesNanos, listOf(0L))
        Assert.assertEquals(report.benchmarks.single().statistics.median, 0L)
    }

    @Test
    fun recomputesP90FromEveryRawSampleInsteadOfWorkerStatistics() {
        val launcher = FixtureLauncher(
            names = listOf("Bench.Only"),
            samples = mapOf("Bench.Only" to (1L..10L).toList())
        )
        val report = BenchmarkCoordinator(launcher, emptyList())
            .run(BenchmarkRequest(null, forks = 1, warmup = 0, iterations = 10))

        val statistics = ObjectMapper().readTree(BenchmarkRenderer.json(report))["benchmarks"][0]["statistics"]
        Assert.assertEquals(statistics["median"].longValue(), 5L)
        Assert.assertEquals(statistics["p90"].longValue(), 9L)
        Assert.assertEquals(statistics["p95"].longValue(), 10L)
    }

    @Test
    fun rejectsEmptyDiscoveryNonzeroExitAndMalformedWorkerDocuments() {
        val empty = FixtureLauncher(names = emptyList())
        val (emptyStdout, emptyStderr) = captureOutput {
            expectCoordinatorFailure {
                BenchmarkCoordinator(empty, emptyList()).run(BenchmarkRequest(null, 1, 0, 1))
            }
        }
        Assert.assertEquals(emptyStdout, "")
        Assert.assertTrue(emptyStderr.contains("worker log must stay captured"), emptyStderr)
        Assert.assertTrue(empty.outputPaths.all { !Files.exists(it) })

        val failed = FixtureLauncher().apply { exitCode = 7 }
        val (failedStdout, failedStderr) = captureOutput {
            expectCoordinatorFailure {
                BenchmarkCoordinator(failed, emptyList()).run(BenchmarkRequest(null, 1, 0, 1))
            }
        }
        Assert.assertEquals(failedStdout, "")
        Assert.assertTrue(failedStderr.contains("worker log must stay captured"), failedStderr)
        Assert.assertTrue(failed.outputPaths.all { !Files.exists(it) })

        val missing = FixtureLauncher(
            names = listOf("Bench.Only"),
            samples = mapOf("Bench.Only" to listOf(1L)),
            outputFor = { arguments, _ ->
                if (arguments.optionValue("-benchmarkName") == null) {
                    """{"schema":"wurst-benchmark-worker-v2","mode":"discovery","benchmarks":["Bench.Only"]}"""
                } else {
                    null
                }
            }
        )
        val (missingStdout, missingStderr) = captureOutput {
            expectCoordinatorFailure {
                BenchmarkCoordinator(missing, emptyList()).run(BenchmarkRequest(null, 1, 0, 1))
            }
        }
        Assert.assertEquals(missingStdout, "")
        Assert.assertTrue(missingStderr.contains("worker log must stay captured"), missingStderr)
        Assert.assertTrue(missing.outputPaths.all { !Files.exists(it) })

        val malformed = object : BenchmarkProcessLauncher {
            val outputPaths = mutableListOf<Path>()

            override fun run(arguments: List<String>): BenchmarkProcessResult {
                val output = Path.of(arguments.optionValue("-benchmarkOutput")!!)
                outputPaths.add(output)
                Files.writeString(output, "not json")
                return BenchmarkProcessResult(0, listOf("malformed worker diagnostic"))
            }
        }
        val (malformedStdout, malformedStderr) = captureOutput {
            expectCoordinatorFailure {
                BenchmarkCoordinator(malformed, emptyList()).run(BenchmarkRequest(null, 1, 0, 1))
            }
        }
        Assert.assertEquals(malformedStdout, "")
        Assert.assertTrue(malformedStderr.contains("malformed worker diagnostic"), malformedStderr)
        Assert.assertTrue(malformed.outputPaths.all { !Files.exists(it) })
    }

    @Test
    fun rejectsTrailingContentAfterWorkerJsonDocument() {
        val launcher = FixtureLauncher(
            outputFor = { arguments, _ ->
                val name = arguments.optionValue("-benchmarkName")
                if (name == null) {
                    """{"schema":"wurst-benchmark-worker-v2","mode":"discovery","benchmarks":["Bench.Only"]} {"extra":true}"""
                } else {
                    workerExecutionJson(name)
                }
            }
        )

        val (_, stderr) = captureOutput {
            expectCoordinatorFailure {
                BenchmarkCoordinator(launcher, emptyList()).run(BenchmarkRequest(null, 1, 0, 1))
            }
        }

        Assert.assertTrue(stderr.contains("worker log must stay captured"), stderr)
        Assert.assertTrue(launcher.outputPaths.all { !Files.exists(it) })
    }

    @Test
    fun rejectsWrongSchemaNameAndChecksumDrift() {
        val wrongSchema = object : BenchmarkProcessLauncher {
            val outputPaths = mutableListOf<Path>()

            override fun run(arguments: List<String>): BenchmarkProcessResult {
                val output = Path.of(arguments.optionValue("-benchmarkOutput")!!)
                outputPaths.add(output)
                val name = arguments.optionValue("-benchmarkName")
                val json = if (name == null) {
                    """{"schema":"wrong","mode":"discovery","benchmarks":["Bench.Only"]}"""
                } else {
                    """{"schema":"wurst-benchmark-worker-v2","mode":"execution","qualifiedName":"Wrong.Name","checksum":1,"batchSize":1,"samplesNanos":[1]}"""
                }
                Files.writeString(output, json)
                return BenchmarkProcessResult(0, listOf("wrong schema diagnostic"))
            }
        }
        val (wrongSchemaStdout, wrongSchemaStderr) = captureOutput {
            expectCoordinatorFailure {
                BenchmarkCoordinator(wrongSchema, emptyList()).run(BenchmarkRequest(null, 1, 0, 1))
            }
        }
        Assert.assertEquals(wrongSchemaStdout, "")
        Assert.assertTrue(wrongSchemaStderr.contains("wrong schema diagnostic"), wrongSchemaStderr)
        Assert.assertTrue(wrongSchema.outputPaths.all { !Files.exists(it) })

        val drift = FixtureLauncher(
            names = listOf("Bench.Only"),
            checksums = mapOf("Bench.Only" to 1),
            outputFor = { arguments, invocation ->
                val name = arguments.optionValue("-benchmarkName")
                if (name == null) {
                    """{"schema":"wurst-benchmark-worker-v2","mode":"discovery","benchmarks":["Bench.Only"]}"""
                } else {
                    val checksum = if (invocation == 1) 2 else 1
                    workerExecutionJson("Bench.Only", checksum = checksum)
                }
            }
        )
        expectCoordinatorFailure {
            BenchmarkCoordinator(drift, emptyList()).run(BenchmarkRequest(null, 2, 0, 1))
        }
        Assert.assertTrue(drift.outputPaths.all { !Files.exists(it) })
    }

    @Test
    fun rejectsOldAndMixedWorkerSchemas() {
        val oldSchema = FixtureLauncher(
            outputFor = { arguments, _ ->
                if (arguments.optionValue("-benchmarkName") == null) {
                    """{"schema":"wurst-benchmark-worker-v1","mode":"discovery","benchmarks":["Bench.Only"]}"""
                } else {
                    """{"schema":"wurst-benchmark-worker-v1","mode":"execution","qualifiedName":"Bench.Only","checksum":1,"batchSize":1,"samplesNanos":[1]}"""
                }
            }
        )
        expectCoordinatorFailure {
            BenchmarkCoordinator(oldSchema, emptyList()).run(BenchmarkRequest(null, 1, 0, 1))
        }

        val mixedSchema = FixtureLauncher(
            outputFor = { arguments, _ ->
                if (arguments.optionValue("-benchmarkName") == null) {
                    """{"schema":"wurst-benchmark-worker-v2","mode":"discovery","benchmarks":["Bench.Only"]}"""
                } else {
                    """{"schema":"wurst-benchmark-worker-v1","mode":"execution","qualifiedName":"Bench.Only","checksum":1,"batchSize":1,"samplesNanos":[1]}"""
                }
            }
        )
        expectCoordinatorFailure {
            BenchmarkCoordinator(mixedSchema, emptyList()).run(BenchmarkRequest(null, 1, 0, 1))
        }
    }

    @Test
    fun rejectsTwoBenchmarkComparisonWhenChecksumsDiffer() {
        val launcher = FixtureLauncher(
            checksums = mapOf(
                "Bench.Fast" to 10,
                "Bench.Slow" to 11
            )
        )

        expectCoordinatorFailure {
            BenchmarkCoordinator(launcher, emptyList()).run(BenchmarkRequest(null, 1, 0, 2))
        }

        Assert.assertTrue(launcher.outputPaths.all { !Files.exists(it) })
    }

    @Test
    fun emitsOnlyDriftingWorkerDiagnosticOnceAndSuppressesSuccessfulWorkerOutput() {
        val launcher = FixtureLauncher(
            names = listOf("Bench.Only"),
            checksums = mapOf("Bench.Only" to 1),
            diagnosticsFor = { arguments, invocation ->
                if (arguments.optionValue("-benchmarkName") != null && invocation == 2) {
                    listOf("drifting worker diagnostic")
                } else {
                    listOf("successful worker diagnostic")
                }
            },
            outputFor = { arguments, invocation ->
                val name = arguments.optionValue("-benchmarkName")
                if (name == null) {
                    """{"schema":"wurst-benchmark-worker-v2","mode":"discovery","benchmarks":["Bench.Only"]}"""
                } else {
                    val checksum = if (invocation == 1) 2 else 1
                    workerExecutionJson("Bench.Only", checksum = checksum)
                }
            }
        )
        val (stdout, stderr) = captureOutput {
            expectCoordinatorFailure {
                BenchmarkCoordinator(launcher, emptyList()).run(BenchmarkRequest(null, 2, 0, 1))
            }
        }

        Assert.assertEquals(stdout, "")
        Assert.assertEquals(stderr.lines().count { it == "drifting worker diagnostic" }, 1, stderr)
        Assert.assertFalse(stderr.contains("successful worker diagnostic"), stderr)
        Assert.assertTrue(launcher.outputPaths.all { !Files.exists(it) })
    }

    @Test
    fun rejectsNonPositiveBatchAndNegativeSamples() {
        listOf(
            """{"schema":"wurst-benchmark-worker-v2","mode":"execution","qualifiedName":"Bench.Only","checksum":1,"batchSize":0,"samplesNanos":[100]}""",
            """{"schema":"wurst-benchmark-worker-v2","mode":"execution","qualifiedName":"Bench.Only","checksum":1,"batchSize":1,"samplesNanos":[-1]}"""
        ).forEach { executionJson ->
            val launcher = object : BenchmarkProcessLauncher {
                val outputPaths = mutableListOf<Path>()

                override fun run(arguments: List<String>): BenchmarkProcessResult {
                    val output = Path.of(arguments.optionValue("-benchmarkOutput")!!)
                    outputPaths.add(output)
                    val name = arguments.optionValue("-benchmarkName")
                    Files.writeString(
                        output,
                        if (name == null) {
                            """{"schema":"wurst-benchmark-worker-v2","mode":"discovery","benchmarks":["Bench.Only"]}"""
                        } else {
                            executionJson
                        }
                    )
                    return BenchmarkProcessResult(0, listOf("invalid worker diagnostic"))
                }
            }
            val (stdout, stderr) = captureOutput {
                expectCoordinatorFailure {
                    BenchmarkCoordinator(launcher, emptyList()).run(BenchmarkRequest(null, 1, 0, 1))
                }
            }
            Assert.assertEquals(stdout, "")
            Assert.assertTrue(stderr.contains("invalid worker diagnostic"), stderr)
            Assert.assertTrue(launcher.outputPaths.all { !Files.exists(it) })
        }
    }

    @Test
    fun rejectsExecutionDocumentsContainingDerivedStatistics() {
        val launcher = FixtureLauncher(
            names = listOf("Bench.Only"),
            outputFor = { arguments, _ ->
                val name = arguments.optionValue("-benchmarkName")
                if (name == null) {
                    """{"schema":"wurst-benchmark-worker-v2","mode":"discovery","benchmarks":["Bench.Only"]}"""
                } else {
                    """{"schema":"wurst-benchmark-worker-v2","mode":"execution","qualifiedName":"Bench.Only","checksum":1,"batchSize":1,"samplesNanos":[100],"statistics":{"mean":100.0,"standardDeviation":0.0,"min":100,"max":100,"median":100,"p90":100,"p95":100}}"""
                }
            }
        )

        val (_, stderr) = captureOutput {
            expectCoordinatorFailure {
                BenchmarkCoordinator(launcher, emptyList()).run(BenchmarkRequest(null, 1, 0, 1))
            }
        }

        Assert.assertTrue(stderr.contains("worker log must stay captured"), stderr)
        Assert.assertTrue(launcher.outputPaths.all { !Files.exists(it) })
    }

    @Test
    fun rendersCleanJsonAndHumanSpeedup() {
        val launcher = FixtureLauncher()
        val report = BenchmarkCoordinator(launcher, emptyList())
            .run(BenchmarkRequest(null, 1, 0, 2))
        val json = BenchmarkRenderer.json(report)
        val expectedDisclaimer =
            "Benchmark results measure the JVM-hosted Wurst IL interpreter. They vary with the machine, JVM, compiler version, host load, and benchmark setup. Use them for controlled side-by-side comparisons under the same conditions, not as absolute Warcraft III, Jass, Lua, or in-game performance numbers."
        val parsed = ObjectMapper().readTree(json)
        Assert.assertEquals(parsed["schema"].textValue(), "wurst-benchmark-v1")
        Assert.assertEquals(parsed["disclaimer"].textValue(), expectedDisclaimer)
        Assert.assertTrue(parsed["benchmarks"][0]["forks"][0]["samplesNanos"].isArray)
        Assert.assertTrue(parsed["benchmarks"][0]["statistics"]["p90"].isIntegralNumber)
        Assert.assertFalse(json.contains("worker log"))

        val human = BenchmarkRenderer.human(report)
        Assert.assertTrue(human.contains("Bench.Fast"))
        Assert.assertTrue(human.contains("median"))
        Assert.assertTrue(human.contains("p90"))
        Assert.assertTrue(human.contains("p95"))
        Assert.assertTrue(human.contains("min/max"))
        Assert.assertTrue(human.contains("checksum"))
        Assert.assertTrue(human.contains("forks"))
        Assert.assertTrue(human.contains("samples"))
        Assert.assertTrue(human.contains("batchSizes 1:2"), human)
        Assert.assertTrue(human.contains("Bench.Fast is"))
        Assert.assertTrue(human.contains("faster than Bench.Slow"))
    }

    @Test
    fun rendersEqualMediansAsTie() {
        val launcher = FixtureLauncher(
            names = listOf("Bench.First", "Bench.Second"),
            samples = mapOf(
                "Bench.First" to listOf(100L),
                "Bench.Second" to listOf(100L)
            )
        )
        val report = BenchmarkCoordinator(launcher, emptyList())
            .run(BenchmarkRequest(null, 1, 0, 1))

        val human = BenchmarkRenderer.human(report)

        Assert.assertTrue(human.contains("Bench.First and Bench.Second have equal median (100 ns/op)"), human)
        Assert.assertFalse(human.contains("faster"), human)
    }

    @Test
    fun avoidsFiniteSpeedupClaimWhenMedianIsBelowTimerResolution() {
        val launcher = FixtureLauncher(
            names = listOf("Bench.Zero", "Bench.Measurable"),
            samples = mapOf(
                "Bench.Zero" to listOf(0L),
                "Bench.Measurable" to listOf(100L)
            )
        )
        val report = BenchmarkCoordinator(launcher, emptyList())
            .run(BenchmarkRequest(null, 1, 0, 1))

        val human = BenchmarkRenderer.human(report)

        Assert.assertTrue(human.contains("Bench.Zero median is below timer resolution"), human)
        Assert.assertFalse(human.contains("x faster"), human)
    }

    @Test
    fun integratedSetupAppUsesInstallationConfigAndKeepsJsonStdoutClean() {
        val project = Files.createTempDirectory("grill-benchmark-project")
        val install = Files.createTempDirectory("grill-benchmark-install")
        val previousInstall = System.getProperty("wurst.install.dir")
        val previousLauncher = SetupApp.benchmarkProcessLauncherOverride
        val previousExitHandler = ExitHandler.handler
        try {
            Files.createDirectories(project.resolve("wurst"))
            Files.createDirectories(project.resolve("_build"))
            Files.writeString(project.resolve("wurst.build"), "name: Demo\ndependencies: []\nscriptMode: lua\nwc3Patch: v2.0\n")
            Files.writeString(project.resolve("_build/core-jass.provenance"), "wc3Patch: v2.0\n")
            Files.write(project.resolve("_build/common.j"), ByteArray(2048) { 'c'.code.toByte() })
            Files.write(project.resolve("_build/blizzard.j"), ByteArray(2048) { 'b'.code.toByte() })
            Files.createDirectories(install.resolve("wurst-compiler"))
            Files.write(install.resolve("wurst-compiler/wurstscript.jar"), byteArrayOf(0))
            System.setProperty("wurst.install.dir", install.toString())
            val launcher = FixtureLauncher(names = listOf("Bench.Only"), samples = mapOf("Bench.Only" to listOf(1L, 2L)))
            SetupApp.benchmarkProcessLauncherOverride = launcher
            val setup = SetupMain().apply { projectRoot = project }
            ExitHandler.handler = { throw CoordinatorExitException(it) }
            val (stdout, stderr) = captureOutput {
                try {
                    setup.doMain(arrayOf("benchmark", "--format", "json", "--iterations", "2"))
                    Assert.fail("benchmark should exit through ExitHandler")
                } catch (exit: CoordinatorExitException) {
                    Assert.assertEquals(exit.code, 0)
                }
            }
            val trimmed = stdout.trim()
            val parser = ObjectMapper().factory.createParser(trimmed)
            val parsed = parser.use {
                val document = ObjectMapper().readTree<com.fasterxml.jackson.databind.JsonNode>(it)
                Assert.assertNotNull(document)
                Assert.assertNull(it.nextToken(), "JSON stdout must contain exactly one document")
                document
            }
            Assert.assertEquals(parsed["schema"].textValue(), "wurst-benchmark-v1")
            val canonicalDisclaimer =
                "Benchmark results measure the JVM-hosted Wurst IL interpreter. They vary with the machine, JVM, compiler version, host load, and benchmark setup. Use them for controlled side-by-side comparisons under the same conditions, not as absolute Warcraft III, Jass, Lua, or in-game performance numbers."
            Assert.assertEquals(parsed["disclaimer"].textValue(), canonicalDisclaimer)
            Assert.assertEquals(
                parsed.fieldNames().asSequence().toSet(),
                setOf("schema", "disclaimer", "environment", "filter", "forks", "warmup", "iterations", "benchmarks")
            )
            Assert.assertEquals(
                parsed["environment"].fieldNames().asSequence().toSet(),
                setOf("os", "jvm", "compiler", "grill", "cpuCount")
            )
            Assert.assertEquals(
                parsed["environment"]["compiler"].textValue(),
                "sha256:6e340b9cffb37a989ca544e6bb780a2c78901d3fb33738768511a30617afa01d"
            )
            val benchmark = parsed["benchmarks"][0]
            Assert.assertEquals(
                benchmark.fieldNames().asSequence().toSet(),
                setOf("qualifiedName", "checksum", "forks", "statistics")
            )
            Assert.assertEquals(
                benchmark["statistics"].fieldNames().asSequence().toSet(),
                setOf("mean", "standardDeviation", "min", "max", "median", "p90", "p95")
            )
            Assert.assertEquals(
                benchmark["forks"][0].fieldNames().asSequence().toSet(),
                setOf("fork", "batchSize", "samplesNanos")
            )
            Assert.assertEquals(stdout, trimmed + System.lineSeparator())
            Assert.assertFalse(stdout.contains("Grill"), stdout)
            Assert.assertFalse(stdout.contains("worker log"), stdout)
            Assert.assertFalse(stderr.contains("worker log"), stderr)
            Assert.assertTrue(launcher.calls.first().contains("-compactOutput"))
        } finally {
            SetupApp.benchmarkProcessLauncherOverride = previousLauncher
            ExitHandler.handler = previousExitHandler
            if (previousInstall == null) System.clearProperty("wurst.install.dir") else System.setProperty("wurst.install.dir", previousInstall)
            deleteTree(project)
            deleteTree(install)
        }
    }

    @Test
    fun benchmarkWorkersRejectOrdinaryModesButKeepCompilerProjectArguments() {
        val project = Files.createTempDirectory("grill-benchmark-argument-validation")
        val install = Files.createTempDirectory("grill-benchmark-argument-install")
        val previousInstall = System.getProperty("wurst.install.dir")
        val previousLauncher = SetupApp.benchmarkProcessLauncherOverride
        val previousExitHandler = ExitHandler.handler
        try {
            Files.createDirectories(project.resolve("wurst"))
            Files.createDirectories(project.resolve("_build/dependencies/dep"))
            Files.createDirectories(project.resolve("_build"))
            Files.writeString(
                project.resolve("wurst.build"),
                "name: Demo\ndependencies:\n  - https://example.invalid/dep\nscriptMode: lua\nwc3Patch: v1.23a\n"
            )
            Files.writeString(project.resolve("_build/core-jass.provenance"), "wc3Patch: v1.23a\n")
            Files.write(project.resolve("_build/common.j"), ByteArray(2048) { 'c'.code.toByte() })
            Files.write(project.resolve("_build/blizzard.j"), ByteArray(2048) { 'b'.code.toByte() })
            Files.createDirectories(install.resolve("wurst-compiler"))
            Files.write(install.resolve("wurst-compiler/wurstscript.jar"), byteArrayOf(0))
            System.setProperty("wurst.install.dir", install.toString())

            val launcher = object : BenchmarkProcessLauncher {
                val calls = mutableListOf<List<String>>()

                override fun run(arguments: List<String>): BenchmarkProcessResult {
                    val ordinaryModes = listOf("-out", "-runcompiletimefunctions")
                    check(ordinaryModes.none(arguments::contains)) {
                        "strict Wurst RunArgs rejected benchmark arguments: $arguments"
                    }
                    check(arguments.contains("-runbenchmarks"))
                    check(arguments.contains("-lua"))
                    check(arguments.contains(project.resolve("wurst").toAbsolutePath().toString()))
                    check(arguments.contains("-lib"))
                    check(arguments.contains(project.resolve("_build/dependencies/dep").toAbsolutePath().toString()))
                    check(arguments.contains(project.resolve("_build/common.j").toAbsolutePath().toString()))
                    check(arguments.contains(project.resolve("_build/blizzard.j").toAbsolutePath().toString()))
                    check(arguments.contains("-noPJass"))
                    check(arguments.contains("-legacyJassChecks"))
                    calls += arguments

                    val output = Path.of(arguments.optionValue("-benchmarkOutput")!!)
                    val name = arguments.optionValue("-benchmarkName")
                    val json = if (name == null) {
                        """{"schema":"wurst-benchmark-worker-v2","mode":"discovery","benchmarks":["Bench.Only"]}"""
                    } else {
                        workerExecutionJson(name, samples = listOf(1L))
                    }
                    Files.writeString(output, json)
                    return BenchmarkProcessResult(0, emptyList())
                }
            }
            SetupApp.benchmarkProcessLauncherOverride = launcher
            val setup = SetupMain().apply {
                projectRoot = project
                parseArgs(listOf("benchmark"))
                noPJass = true
                benchmarkForks = 1
                benchmarkWarmup = 0
                benchmarkIterations = 1
            }
            ExitHandler.handler = { throw CoordinatorExitException(it) }

            val exitCode = try {
                SetupApp.handleArgs(setup)
                -1
            } catch (exit: CoordinatorExitException) {
                exit.code
            }

            Assert.assertEquals(exitCode, 0)
            Assert.assertEquals(launcher.calls.size, 2)
        } finally {
            SetupApp.benchmarkProcessLauncherOverride = previousLauncher
            ExitHandler.handler = previousExitHandler
            if (previousInstall == null) System.clearProperty("wurst.install.dir") else System.setProperty("wurst.install.dir", previousInstall)
            deleteTree(project)
            deleteTree(install)
        }
    }

    @Test
    fun integratedSetupAppReturnsNonzeroOnCoordinatorFailure() {
        val project = Files.createTempDirectory("grill-benchmark-failure-project")
        val install = Files.createTempDirectory("grill-benchmark-failure-install")
        val previousInstall = System.getProperty("wurst.install.dir")
        val previousLauncher = SetupApp.benchmarkProcessLauncherOverride
        val previousExitHandler = ExitHandler.handler
        try {
            Files.createDirectories(project.resolve("wurst"))
            Files.writeString(project.resolve("wurst.build"), "name: Demo\ndependencies: []\nscriptMode: lua\nwc3Patch: v2.0\n")
            Files.createDirectories(install.resolve("wurst-compiler"))
            Files.write(install.resolve("wurst-compiler/wurstscript.jar"), byteArrayOf(0))
            System.setProperty("wurst.install.dir", install.toString())
            SetupApp.benchmarkProcessLauncherOverride = FixtureLauncher().apply { exitCode = 3 }
            val setup = SetupMain().apply { projectRoot = project }
            ExitHandler.handler = { throw CoordinatorExitException(it) }
            val (stdout, stderr) = captureOutput {
                val exitCode = try {
                    SetupApp.handleArgs(setup.apply { parseArgs(listOf("benchmark", "--format", "json")) })
                    -1
                } catch (exit: CoordinatorExitException) {
                    exit.code
                }
                Assert.assertEquals(exitCode, 1)
            }
            Assert.assertEquals(stdout, "")
            Assert.assertTrue(stderr.contains("worker log must stay captured"), stderr)
            Assert.assertTrue(stderr.contains("Wurst benchmark failed"), stderr)
        } finally {
            SetupApp.benchmarkProcessLauncherOverride = previousLauncher
            ExitHandler.handler = previousExitHandler
            if (previousInstall == null) System.clearProperty("wurst.install.dir") else System.setProperty("wurst.install.dir", previousInstall)
            deleteTree(project)
            deleteTree(install)
        }
    }
}
