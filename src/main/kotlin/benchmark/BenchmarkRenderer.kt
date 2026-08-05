package benchmark

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import java.util.Locale

object BenchmarkRenderer {
    private val mapper = ObjectMapper()

    fun json(report: BenchmarkReport): String {
        val root = mapper.createObjectNode()
        root.put("schema", report.schema)
        root.put("disclaimer", report.disclaimer)
        root.set<ObjectNode>("environment", environment(report.environment))
        if (report.filter == null) root.putNull("filter") else root.put("filter", report.filter)
        root.put("forks", report.forks)
        root.put("warmup", report.warmup)
        root.put("iterations", report.iterations)
        val benchmarks = mapper.createArrayNode()
        report.benchmarks.forEach { benchmarks.add(benchmark(it)) }
        root.set<ArrayNode>("benchmarks", benchmarks)
        return mapper.writeValueAsString(root)
    }

    fun human(report: BenchmarkReport): String {
        val lines = mutableListOf<String>()
        report.benchmarks.forEach { benchmark ->
            val stats = benchmark.statistics
            val sampleCount = benchmark.forks.sumOf { it.samplesNanos.size }
            val batchSizes = benchmark.forks.joinToString(",") { "${it.fork}:${it.batchSize}" }
            lines += "${benchmark.qualifiedName} median ${stats.median} ns/op p90 ${stats.p90} ns/op p95 ${stats.p95} ns/op " +
                "min/max ${stats.min}/${stats.max} ns/op checksum ${benchmark.checksum} " +
                "forks ${benchmark.forks.size} samples $sampleCount batchSizes $batchSizes"
        }
        if (report.benchmarks.size == 2) {
            val first = report.benchmarks[0]
            val second = report.benchmarks[1]
            if (first.statistics.median == second.statistics.median) {
                lines += "${first.qualifiedName} and ${second.qualifiedName} have equal median (${first.statistics.median} ns/op)"
            } else {
                val faster: BenchmarkAggregate
                val slower: BenchmarkAggregate
                if (first.statistics.median <= second.statistics.median) {
                    faster = first
                    slower = second
                } else {
                    faster = second
                    slower = first
                }
                if (faster.statistics.median == 0L) {
                    lines += "${faster.qualifiedName} median is below timer resolution; no finite speedup ratio can be reported against ${slower.qualifiedName}"
                } else {
                    val ratio = slower.statistics.median.toDouble() / faster.statistics.median
                    lines += "${faster.qualifiedName} is ${String.format(Locale.ROOT, "%.2fx", ratio)} faster than ${slower.qualifiedName}"
                }
            }
        }
        lines += ""
        lines += report.disclaimer
        return lines.joinToString("\n")
    }

    private fun environment(environment: BenchmarkEnvironment): ObjectNode {
        val node = mapper.createObjectNode()
        node.put("os", environment.os)
        node.put("jvm", environment.jvm)
        node.put("compiler", environment.compiler)
        node.put("grill", environment.grill)
        node.put("cpuCount", environment.cpuCount)
        return node
    }

    private fun benchmark(benchmark: BenchmarkAggregate): ObjectNode {
        val node = mapper.createObjectNode()
        node.put("qualifiedName", benchmark.qualifiedName)
        node.put("checksum", benchmark.checksum)
        val forks = mapper.createArrayNode()
        benchmark.forks.forEach { fork ->
            val forkNode = mapper.createObjectNode()
            forkNode.put("fork", fork.fork)
            forkNode.put("batchSize", fork.batchSize)
            val samples = mapper.createArrayNode()
            fork.samplesNanos.forEach(samples::add)
            forkNode.set<ArrayNode>("samplesNanos", samples)
            forks.add(forkNode)
        }
        node.set<ArrayNode>("forks", forks)
        val stats = mapper.createObjectNode()
        stats.put("mean", benchmark.statistics.mean)
        stats.put("standardDeviation", benchmark.statistics.standardDeviation)
        stats.put("min", benchmark.statistics.min)
        stats.put("max", benchmark.statistics.max)
        stats.put("median", benchmark.statistics.median)
        stats.put("p90", benchmark.statistics.p90)
        stats.put("p95", benchmark.statistics.p95)
        node.set<ObjectNode>("statistics", stats)
        return node
    }
}
