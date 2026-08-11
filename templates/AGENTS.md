<!-- WURST_AGENTS_TEMPLATE_VERSION: 2026-08-08 -->
# AGENTS.md - WurstScript Map Project Notes

WurstScript Warcraft III map project notes for editing `.wurst` code, dependencies, generated objects, tests, or map build logic.

## Read On Demand

Keep this file in context. Read deeper references only when the task needs them:

- **Language semantics**: read `~/.wurst/wurst-compiler/agent-docs/WURST_LANGUAGE.md` when installed so the reference matches the local compiler; otherwise use https://wurstlang.org/manual.html.
- **Stdlib APIs**: search `_build/dependencies/wurstStdlib2/wurst/` before writing a native call or new infrastructure. Read its `AGENTS.md` when present.
- **Other dependencies**: before changing code that uses one, inspect `_build/dependencies/<dep>/` and read its `AGENTS.md` or usage guides first.
- **Project conventions**: inspect nearby working code and project-local notes before guessing syntax, APIs, or style.

## Source And Scope

- Change source packages, configuration, and tests; never treat `_build/` or downloaded dependencies as source-of-truth. Patch an upstream dependency repository instead of its installed copy.
- Prefer small, maintainable changes that address the root cause. Avoid unrelated refactors, duplicated branches, and special-case patches.
- Keep packages focused and below roughly 500 lines; split by feature, responsibility, or data type when useful.
- Add or update narrow tests for changed behavior, parsing, compiletime generation, or shared utilities.
- Fix relevant compiler warnings unless intentionally suppressed and explained.

## Stdlib First

Use Wurst stdlib and dependency APIs instead of ported JASS or hand-built engine infrastructure. Search the stdlib before calling a raw `common.j`/`Blizzard.j` native; if no wrapper exists, add a one-line comment recording that search. The normal `CreateTrigger()..register...()..addAction() ->` cascade is an accepted Wurst idiom.

Do not reimplement systems already provided by packages such as `ClosureTimers`, `ClosureEvents`, `ClosureForGroups`, `GroupUtils`, `DummyCaster`, `DamageEvent`, `Fx`, `SoundUtils`, `Orders`, or the stdlib collections. If an API nearly fits, prefer a thin wrapper and document the remaining mismatch.

## Project Configuration

`wurst.build` is the authoritative project YAML:

- `scriptMode` (`LUA` or `JASS`) selects compiler output.
- `wc3Patch` selects compatible core JASS and the stdlib era.
- `dependencies` lists Git URLs managed by `grill`; the default is usually `wurstStdlib2`.
- `buildMapData` controls metadata written to the output `.w3x`.

Read `scriptMode` before adding or removing `execute()` or timer chunking. Do not infer the build/typecheck target from the locally installed Warcraft III client; client compatibility is a separate launch concern.

- **Lua**: no practical op-limit. Do not add `execute()` as an op-limit workaround; use timers for actual asynchronous delay.
- **Jass**: the VM has an operation limit per thread. Heavy work may require `execute()` or chunking across ticks.

## High-Risk Wurst Semantics

- Closures capture locals by value. Assigning inside a callback does not update the captured outer local. Keep creation and follow-up handlers in the same closure, store shared mutable state on an owning class, or use `reference(value)` and destroy it when finished.
- Wurst class lifetime remains explicit for Lua output. Objects created with `new`, stored closures/listeners, references, and owned collections usually need `destroy`; owners should clear stale references after destruction and must avoid double-destroy.
- WC3 `int` is signed 32-bit and overflows silently. Promote before multiplication (`worth.toReal() * count`), never after an integer expression has already overflowed.
- Lambdas require a known target type. Lambdas used as `code` cannot accept parameters or capture locals.
- Every `.wurst` source belongs to a package and uses indentation-defined blocks. Package exports require `public`; imports are not re-exported unless declared `import public`.

## Compiletime Objects

Use compiletime generation and stable ID helpers for object-editor data. New generated objects must use real melee objects as bases, never other custom objects. Melee bases carry abilities, costs, upgrades, requirements, stock, food, race, classification, art, sound, and tooltip fields; explicitly clear inherited side effects for the object family. Regression tests should assert dangerous fields are absent as well as intended fields being present.

## Task-Specific References

- For custom UI, read the UI dependency's guides before editing. In particular, `wurst-table-layout` provides `AGENTS.md`, `AI_USAGE.md`, and `WC3_FRAMEHANDLE_GUIDE.md`; its rules own frame lifecycle, parenting, safe-area, and multiplayer behavior.
- For unfamiliar stdlib or dependency APIs, search declarations and nearby usage rather than inventing signatures.
- For map object data, determine the authoritative compiletime source before changing generated output.

## Validate

```bash
grill install
grill typecheck --quiet
grill test --quiet
```

If a quiet check fails, rerun the smallest relevant package, file, or test without `--quiet`. Avoid broad noisy reruns when the failure supplies a useful target.

For map build changes, run `grill build ExampleMap.w3x --quiet`. Builds are production mode by default; add `--dev` only when validating code that requires `isProductionBuild() == false`. Use `grill exportobjects <mapfile|folder>` to dump object-editor data to Wurst source.

Done means the focused checks pass and relevant errors or warnings are fixed or explicitly explained. Runtime/UI behavior that static checks cannot prove still requires the smallest suitable Warcraft III or e2e verification.
