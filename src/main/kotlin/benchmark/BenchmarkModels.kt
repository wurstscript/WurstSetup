package benchmark

const val BENCHMARK_WORKER_SCHEMA = "wurst-benchmark-worker-v2"
const val BENCHMARK_SCHEMA = "wurst-benchmark-v1"
const val BENCHMARK_DISCLAIMER =
    "Benchmark results measure the JVM-hosted Wurst IL interpreter. They vary with the machine, JVM, compiler version, host load, and benchmark setup. Use them for controlled side-by-side comparisons under the same conditions, not as absolute Warcraft III, Jass, Lua, or in-game performance numbers."

fun interface BenchmarkProcessLauncher {
    fun run(arguments: List<String>): BenchmarkProcessResult
}

data class BenchmarkProcessResult(val exitCode: Int, val output: List<String>)

data class BenchmarkRequest(
    val filter: String? = null,
    val forks: Int = 3,
    val warmup: Int = 5,
    val iterations: Int = 10
) {
    init {
        require(forks > 0) { "forks must be positive" }
        require(warmup >= 0) { "warmup must be non-negative" }
        require(iterations > 0) { "iterations must be positive" }
    }
}

data class BenchmarkEnvironment(
    val os: String,
    val jvm: String,
    val compiler: String,
    val grill: String,
    val cpuCount: Int
)

data class BenchmarkStatistics(
    val mean: Double,
    val standardDeviation: Double,
    val min: Long,
    val max: Long,
    val median: Long,
    val p90: Long,
    val p95: Long
) {
    companion object {
        fun fromSamples(samples: List<Long>): BenchmarkStatistics {
            require(samples.isNotEmpty()) { "at least one benchmark sample is required" }
            require(samples.all { it >= 0 }) { "benchmark samples must be non-negative" }
            val sorted = samples.sorted()
            val mean = samples.average()
            val variance = samples
                .map { sample ->
                    val delta = sample - mean
                    delta * delta
                }
                .average()
            fun nearestRank(percentile: Double): Long {
                val rank = kotlin.math.ceil(percentile * sorted.size).toInt().coerceAtLeast(1)
                return sorted[rank - 1]
            }
            return BenchmarkStatistics(
                mean = mean,
                standardDeviation = kotlin.math.sqrt(variance),
                min = sorted.first(),
                max = sorted.last(),
                median = nearestRank(0.50),
                p90 = nearestRank(0.90),
                p95 = nearestRank(0.95)
            )
        }
    }
}

data class BenchmarkFork(
    val fork: Int,
    val batchSize: Int,
    val samplesNanos: List<Long>
)

data class BenchmarkAggregate(
    val qualifiedName: String,
    val checksum: Int,
    val forks: List<BenchmarkFork>,
    val statistics: BenchmarkStatistics
)

data class BenchmarkReport(
    val schema: String = BENCHMARK_SCHEMA,
    val disclaimer: String = BENCHMARK_DISCLAIMER,
    val environment: BenchmarkEnvironment,
    val filter: String?,
    val forks: Int,
    val warmup: Int,
    val iterations: Int,
    val benchmarks: List<BenchmarkAggregate>
)
