# AGENTS.md - WurstSetup / Grill Notes

This repo builds the Grill CLI and project setup tooling. The generated map-project agent notes live in `templates/AGENTS.md`; keep this root file focused on WurstSetup itself.

## Editing templates/AGENTS.md

- The first line marker must stay in lockstep with `SetupApp.AGENTS_TEMPLATE_VERSION` (a test enforces this); bump both when changing the template.
- Keep the intro line starting with "WurstScript Warcraft III map project notes" — `AGENTS_TEMPLATE_SOURCE_HINT` matches on it to detect template-derived files.
- Grill fetches the template from the raw GitHub URL on master, so pushing template changes publishes them to `grill install` users immediately. Only document language features after a compiler release ships them.
- Keep the template token-lean: it is loaded into every agent session in user projects. Prefer pointers to on-demand docs (stdlib/dependency `AGENTS.md`, the online manual) over inlining more content.

## Current Architecture

- Project config models come from `com.github.wurstscript:wurst-project-config`; do not reintroduce local DAO copies.
- Local helpers in `config/ProjectConfigModels.kt` should stay thin: typealiases plus immutable copy helpers for shared records.
- `YamlHelper.dumpProjectConfig` intentionally serializes a pruned YAML map instead of the shared records directly. This preserves the old user-facing `wurst.build` behavior by omitting null/default nested fields.
- `wbschema.json` should stay lenient and aligned with the shared config parser, especially for `scriptMode`, `wc3Patch`, and nullable legacy fields.

## WC3 Patch And Core JASS

- `CoreJassProvider.DEFAULT_PATCH` is `v2.0`.
- Core JASS is fetched from `wurstscript/jass-history`.
- Friendly patch targets must resolve through the shared parser / `Wc3PatchTarget` style rules:
  - below `1.29` => pre-1.29 behavior and stdlib
  - `1.29` through `1.31` => classic
  - `1.32+`, `1.36`, `2.0`, and `Reforged-*` => Reforged
- Do not add alias hacks for broken jass-history folder names. Fix `wurstscript/jass-history` instead.
- Bundled core JASS fallbacks are patch-specific. Do not silently use the old `reforged` bundle as the `v2.0` fallback.
- Keep provenance in `_build/core-jass.properties`; mismatched cached `common.j` / `blizzard.j` should be refreshed.

## Generate Workflow

- `grill generate` should write a schema-valid minimal `wurst.build`.
- Keep `dependencies`, `scriptMode`, and `wc3Patch`.
- `buildMapData` should only seed known fields: `name`, `fileName`, and `author`. Do not emit nested default scaffolding like `scenarioData`, `optionsFlags`, `players: []`, `forces: []`, or `loadingScreen: null`.
- Generate supports `--wc3-path <dir>`. It should detect/show the Warcraft III client family and warn if it does not match the selected project patch target.
- The VS Code setting `wurst.wc3path` should only be written for a valid detected/selected WC3 folder.

## Install / Userdir

- Grill installs itself to `~/.wurst/grill-cli/grill.jar`.
- The Gradle task `make_for_userdir` must keep that jar refreshed for local testing.
- `remove wurstscript` must not delete the whole `~/.wurst` folder; it should remove compiler artifacts only and leave Grill/runtime state intact.
- Tests should not mutate the real user install. Use the `wurst.install.dir` system property override when testing installation/removal behavior.

## Compiler Interaction

- Build/typecheck should not require parsing the installed Warcraft executable when `wc3Patch` is pinned.
- Run/launch is different: the selected WC3 executable controls launch arguments. If the client family and project patch target differ, warn and allow choosing another WC3 folder.
- Keep compiler-facing patch behavior tested in the WurstScript repo as well; Grill and compiler can diverge if only one side is tested.

## Test Commands

Run the full WurstSetup suite before publishing:

```bash
./gradlew test
```

Useful focused checks:

```bash
./gradlew test --tests GenerateTests --tests YamlHelperTests --tests Wc3ClientDetectorTests
./gradlew test --tests CMDTests.testUnInstallCmd
./gradlew make_for_userdir
```

`git diff --check` should be clean apart from Windows CRLF warnings.
