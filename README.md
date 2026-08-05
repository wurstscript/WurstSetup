[![codecov](https://codecov.io/gh/wurstscript/WurstSetup/branch/master/graph/badge.svg)](https://codecov.io/gh/wurstscript/WurstSetup)


# WurstScript Setup App

Allows automated installation of a wurstscript environment and project setup.

## Grill

Grill is the name of the CLI and dependency manager used by the UI internally.
Pro users can make use of grill from the shell:

### Update/Remove wurst installation

Update or remove the global wurst installation by using the special `wurstscript` identifier.

```cmd
> grill install wurstscript
> grill remove wurstscript
```

### Creating a new project

To create a new project, use `generate` and supply your name of choice.

```cmd
> grill generate <project_name>
```

### Updating a project

By not passing any additional arguments grill will assume that the execution location is a wurst project.

To update all project dependencies use:

```cmd
> grill install
```

To add a new dependency to your project, use:

```cmd
> grill install <git_url>
```

### Testing a project

Use `test` to compile the project at the current location and run unit tests.

```cmd
> grill test
```

### Typechecking a project

Use `typecheck` to compile/typecheck the project without building an output map.
The command exits with code `0` when compilation succeeds and `1` on compilation errors.

```cmd
> grill typecheck
```

### Checking dependency updates

Use `outdated` to check whether any dependency is not on the latest commit of its configured branch
(or repository default branch when none is specified).
The command exits with code `0` when dependencies are up to date and `1` when updates are available.

```cmd
> grill outdated
```

### Benchmarking Wurst functions

`grill benchmark` measures Wurst code in the JVM-hosted Wurst IL interpreter. A benchmark is a package-level, parameterless function annotated with `@benchmark` and returning `int`; its return value must be a stable, workload-derived checksum.

```wurst
import Wurstunit

@test @benchmark function benchmarkName() returns int
    var checksum = 0
    for i = 0 to 999
        checksum += i
    checksum.assertEquals(499500)
    return checksum
```

The checksum should depend on the work being measured, not on a clock, random value, object identity, or mutable global state. When comparing two implementations, use the same inputs, operation count, and checksum calculation in both functions. A changing checksum indicates a correctness or benchmark-design problem and causes the comparison to fail.

For very small operations, batch many fixed inputs inside one benchmark invocation and return one checksum for the complete batch. This makes the measured workload large enough to distinguish implementations; the runner also reports the calibrated invocation `batchSize` for each fork. A benchmark may also carry @test. In normal test mode its assertions run and the int return is ignored; in benchmark mode the same return is the checksum. Use benchmark-only functions when running the workload during every test suite would be too expensive.

Run all benchmarks or select package/function names with an optional substring filter:

```cmd
> grill benchmark
> grill benchmark Polygon
> grill benchmark Polygon --forks 5 --warmup 5 --iterations 20
> grill benchmark Polygon --format json
> grill benchmark --help
```

Options are:

- `[filter]` — optional substring used to select benchmark names.
- `--forks N` — positive number of isolated compiler JVMs per benchmark, serially (default `3`).
- `--warmup N` — non-negative number of unmeasured warmup samples per fork (default `5`).
- `--iterations N` — positive number of measured samples per fork (default `10`).
- `--format human|json` — concise comparison output or machine-readable `wurst-benchmark-v1` JSON (default `human`).
- `--help` — show benchmark-specific help without loading the project.

Global options such as `-projectDir`, `--quiet`, and `--debug` remain available.

Use JSON when a script needs raw samples, checksums, statistics, and environment metadata; diagnostics are kept off JSON stdout. The `environment.compiler` field is `sha256:<lowercase hex>` for the exact compiler JAR used by the workers. For credible relative results, keep the machine, OS, Java runtime, compiler and Grill versions, project inputs, fork/warmup/iteration settings, and background load consistent. Pin the process to dedicated CPU cores and avoid thermal or power-state changes where practical; `grill benchmark` does not itself control CPU affinity, frequency scaling, garbage collection, or other host-level noise.

Benchmark results measure the JVM-hosted Wurst IL interpreter. They vary with the machine, JVM, compiler version, host load, and benchmark setup. Use them for controlled side-by-side comparisons under the same conditions, not as absolute Warcraft III, Jass, Lua, or in-game performance numbers.


### Building the project

Use `build` to generate an output map according to `wurst.build` specifications.

```cmd
> grill build
```

Use `--dev` to build the output map in run/development mode. This makes compiletime
`isProductionBuild()` return `false` while still writing a map file.

```cmd
> grill build ExampleMap.w3x --dev
```

## How it works

### Wurst Installation

The wurst compiler gets downloaded into the users home directory into a wurst folder `~/.wurst`

### Project Generation

The setup app downloads this repo https://github.com/wurstscript/wurst-project-template as a wurst project template and then inserts the necessary local paths.
Dependencies are stored in `_build/dependencies/`.
