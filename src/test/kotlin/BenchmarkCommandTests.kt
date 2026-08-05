import file.BenchmarkFormat
import file.CLICommand
import file.ExitHandler
import file.SetupMain
import file.SetupApp
import org.testng.Assert
import org.testng.annotations.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Files
import java.nio.charset.StandardCharsets

private const val CANONICAL_BENCHMARK_DISCLAIMER =
    "Benchmark results measure the JVM-hosted Wurst IL interpreter. They vary with the machine, JVM, compiler version, host load, and benchmark setup. Use them for controlled side-by-side comparisons under the same conditions, not as absolute Warcraft III, Jass, Lua, or in-game performance numbers."

private class BenchmarkExitException(val code: Int) : RuntimeException("exit $code")

private fun benchmarkExitCode(block: () -> Unit): Int {
    val previousHandler = ExitHandler.handler
    return try {
        ExitHandler.handler = { throw BenchmarkExitException(it) }
        block()
        -1
    } catch (exception: BenchmarkExitException) {
        exception.code
    } finally {
        ExitHandler.handler = previousHandler
    }
}

private fun captureStdout(block: () -> Unit): String {
    val previousOut = System.out
    val output = ByteArrayOutputStream()
    val capturedOut = PrintStream(output, true, StandardCharsets.UTF_8)
    System.setOut(capturedOut)
    try {
        block()
    } finally {
        capturedOut.flush()
        System.setOut(previousOut)
    }
    return output.toString(StandardCharsets.UTF_8)
}

class BenchmarkCommandTests {

    @Test
    fun generalHelpSurfacesBenchmarkCommand() {
        Assert.assertTrue(SetupApp.guiHelpText().contains("grill benchmark [filter]"))
        Assert.assertTrue(SetupApp.commandHelpText().contains("benchmark [filter]"))
    }

    @Test
    fun benchmarkAcceptsGlobalProjectAndDiagnosticOptions() {
        val projectRoot = Files.createTempDirectory("grill-benchmark-global-options")
        try {
            val setup = SetupMain()

            val exitCode = benchmarkExitCode {
                setup.parseArgs(
                    listOf(
                        "benchmark",
                        "Polygon",
                        "-projectDir", projectRoot.toString(),
                        "--quiet",
                        "--debug"
                    )
                )
            }

            Assert.assertEquals(exitCode, -1)
            Assert.assertEquals(setup.projectRoot, projectRoot)
            Assert.assertTrue(setup.quiet)
            Assert.assertTrue(setup.debug)
            Assert.assertEquals(setup.commandArg, "Polygon")
        } finally {
            Files.deleteIfExists(projectRoot)
        }
    }

    @Test
    fun parsesBenchmarkOptionsAndFilter() {
        val setup = SetupMain()

        setup.parseArgs(
            listOf(
                "benchmark",
                "Polygon",
                "--forks", "3",
                "--warmup", "5",
                "--iterations", "10",
                "--format", "json"
            )
        )

        Assert.assertEquals(setup.command, CLICommand.BENCHMARK)
        Assert.assertEquals(setup.commandArg, "Polygon")
        Assert.assertEquals(setup.benchmarkForks, 3)
        Assert.assertEquals(setup.benchmarkWarmup, 5)
        Assert.assertEquals(setup.benchmarkIterations, 10)
        Assert.assertEquals(setup.benchmarkFormat, BenchmarkFormat.JSON)
    }

    @Test
    fun usesBenchmarkDefaults() {
        val setup = SetupMain()

        setup.parseArgs(listOf("benchmark"))

        Assert.assertEquals(setup.command, CLICommand.BENCHMARK)
        Assert.assertTrue(setup.commandArg.isEmpty())
        Assert.assertEquals(setup.benchmarkForks, 3)
        Assert.assertEquals(setup.benchmarkWarmup, 5)
        Assert.assertEquals(setup.benchmarkIterations, 10)
        Assert.assertEquals(setup.benchmarkFormat, BenchmarkFormat.HUMAN)
    }

    @Test
    fun acceptsZeroWarmupIterations() {
        val setup = SetupMain()

        val exitCode = benchmarkExitCode {
            setup.parseArgs(listOf("benchmark", "--warmup", "0"))
        }

        Assert.assertEquals(exitCode, -1)
        Assert.assertEquals(setup.benchmarkWarmup, 0)
    }

    @Test
    fun benchmarkHelpShowsOnlyBenchmarkHelpWithoutProjectSetup() {
        val projectRoot = Files.createTempDirectory("grill-benchmark-help")
        try {
            Files.writeString(projectRoot.resolve("wurst.build"), "not valid project config")
            val setup = SetupMain().apply { this.projectRoot = projectRoot }

            var exitCode = -1
            val output = captureStdout {
                exitCode = benchmarkExitCode {
                    setup.doMain(arrayOf("benchmark", "--help"))
                }
            }

            Assert.assertEquals(exitCode, 0)
            Assert.assertTrue(setup.benchmarkHelp)
            Assert.assertTrue(output.contains("grill benchmark [filter]"), output)
            Assert.assertTrue(output.contains("--forks N"), output)
            Assert.assertTrue(output.contains("--warmup N"), output)
            Assert.assertTrue(output.contains("--iterations N"), output)
            Assert.assertTrue(output.contains("--format human|json"), output)
            Assert.assertTrue(output.contains("environment.compiler"), output)
            Assert.assertTrue(output.contains("sha256:<lowercase hex>"), output)
            Assert.assertTrue(output.contains(CANONICAL_BENCHMARK_DISCLAIMER), output)
        } finally {
            Files.walk(projectRoot).sorted(Comparator.reverseOrder()).forEach(Files::deleteIfExists)
        }
    }

    @Test
    fun rejectsEveryInvalidIntegerCategory() {
        val invalidValuesByOption = mapOf(
            "--forks" to listOf("0", "-1", "not-a-number"),
            "--warmup" to listOf("-1", "not-a-number"),
            "--iterations" to listOf("0", "-1", "not-a-number")
        )

        for ((option, invalidValues) in invalidValuesByOption) {
            for (value in invalidValues) {
                val code = benchmarkExitCode {
                    SetupMain().parseArgs(listOf("benchmark", option, value))
                }
                Assert.assertEquals(code, 1, "$option $value should be rejected")
            }
        }
    }

    @Test
    fun rejectsMissingIntegerValues() {
        for (option in listOf("--forks", "--warmup", "--iterations")) {
            val code = benchmarkExitCode {
                SetupMain().parseArgs(listOf("benchmark", option))
            }
            Assert.assertEquals(code, 1, "$option without a value should be rejected")
        }
    }

    @Test
    fun rejectsUnsupportedAndMissingFormats() {
        for (args in listOf(
            listOf("benchmark", "--format", "xml"),
            listOf("benchmark", "--format")
        )) {
            val code = benchmarkExitCode {
                SetupMain().parseArgs(args)
            }
            Assert.assertEquals(code, 1, "${args.joinToString(" ")} should be rejected")
        }
    }

    @Test
    fun rejectsUnexpectedExtraPositionalArguments() {
        val code = benchmarkExitCode {
            SetupMain().parseArgs(listOf("benchmark", "first", "second"))
        }

        Assert.assertEquals(code, 1)
    }

    @Test
    fun readmeDocumentsTheSameBenchmarkContractAsHelp() {
        val readme = Files.readString(java.nio.file.Path.of("README.md"), StandardCharsets.UTF_8)

        Assert.assertTrue(readme.contains(CANONICAL_BENCHMARK_DISCLAIMER), "README must contain the canonical disclaimer")
        Assert.assertTrue(readme.contains("@benchmark function benchmarkName() returns int"), readme)
        Assert.assertTrue(readme.contains("--forks N"), readme)
        Assert.assertTrue(readme.contains("--warmup N"), readme)
        Assert.assertTrue(readme.contains("--iterations N"), readme)
        Assert.assertTrue(readme.contains("--format human|json"), readme)
        Assert.assertTrue(readme.contains("environment.compiler"), readme)
        Assert.assertTrue(readme.contains("sha256:<lowercase hex>"), readme)
        Assert.assertTrue(readme.contains("workload-derived checksum"), readme)
    }
}
