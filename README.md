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

### Aligning with the installed Warcraft III patch

Use `patch` to compare the project target with the exact installed Warcraft III version. This is read-only:

```cmd
> grill patch
```

When Grill reports a mismatch, migrate the project configuration, official stdlib branch, and cached core JASS together:

```cmd
> grill patch align
```

Grill reads the configured VS Code game path first and falls back to automatic detection. Pass `--wc3-path <dir>` to select a different installation. Alignment creates `wurst.build.bak` and makes no changes if an exact supported client patch cannot be detected.

`grill install` also keeps the official stdlib dependency pinned to the branch required by the project's existing `wc3Patch`, and only suggests migration when it detects a different client patch.


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
