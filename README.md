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

For an offline dependency, pass an absolute `file:` URL to a local directory. `grill install file:///...` refreshes that dependency without fetching the project's other remote dependencies. Grill copies its contents into `_build/dependencies/<directory-name>` (the source `.git` directory is excluded; symlinks are rejected):

```cmd
> grill install file:///C:/Users/me/wurst/MyDependency
```

Private HTTPS repositories use the credential helpers configured for your installed Git, including Git Credential Manager. If sign-in is needed, the helper can open its normal interactive flow. Grill does not store credentials itself.

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

### Running the project in Warcraft III

Use `run` to build a map using `wurst.build` and launch the resulting map in Warcraft III:

```cmd
> grill run ExampleMap.w3x
> grill run ExampleMap.w3x --dev --wc3-path "C:\Program Files (x86)\Warcraft III"
```

Like `build`, `run` accepts map archives or folders and auto-detects the input when the project root contains exactly one map. It uses the same build defaults and flags, including `--dev`, `--noPJass`, `--measure`, `--quiet`, and `-projectDir`.

Grill selects Warcraft III from `--wc3-path`, then a valid `.vscode/settings.json` path (`wurst.wc3path`), then automatic detection. It warns about project/client patch mismatches; use `--wc3-path` to choose another client. The built map is passed via `-loadfile`; Reforged also receives `-launch` and `-editor` to preserve the authentication session. Older clients receive a copy in `Maps/Test/GrillRunMap.w3x`, under the installation for 1.27 and older or the Warcraft III documents folder for 1.28–1.31. Linux launches through Wine and respects `WINEPREFIX` for the documents location. Build failures prevent launch. The command returns after starting Warcraft III.

### Hot reloading a Lua map

A Lua project (`scriptMode: lua`) can change the code of a running single-player game without restarting it:

```cmd
> grill run --hot ExampleMap.w3x
  ... edit the Wurst code ...
> grill reload
```

`run --hot` builds the map with the compiler's `-luaHotReload` flag, prepares a hot reload session for `_build`, and launches Warcraft III with a copy of the map in `_build/grill/hot-run`, so that the game does not hold the file each reload builds again. The new session and its saved build options become active only after the launcher succeeds. If launching fails, the previous session, unread payloads, and saved map/settings are restored. This requires a compiler supporting staged hot reload launches. After each edit, `reload` builds the project again, with the map and `--dev` setting of the hot run unless you name another map, and writes the next payload for the running map to pick up. It prints the payload and any warnings. When the edit cannot be hot reloaded (for example a field's type changed), or no map was started with `run --hot` (or a hot run from VS Code), it prints the reasons, writes nothing and exits with 1; rebuild and restart with `run --hot`.

Payloads go to the `CustomMapData` folder of the Warcraft III documents folder (the same documents folder `run` uses), unless `--custom-map-data <dir>` or the VS Code setting `wurst.customMapDataPath` names another folder. Hot reload is Lua only: Jass projects are rejected; use Jass Hot Code Reload (JHCR) for them. What can be reloaded is described in the compiler's `docs/LUA_HOT_RELOAD.md`.

## How it works

### Wurst Installation

The wurst compiler gets downloaded into the users home directory into a wurst folder `~/.wurst`

### Project Generation

The setup app downloads this repo https://github.com/wurstscript/wurst-project-template as a wurst project template and then inserts the necessary local paths.
Dependencies are stored in `_build/dependencies/`.
