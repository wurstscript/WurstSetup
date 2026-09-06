<!-- WURST_AGENTS_TEMPLATE_VERSION: 2026-09-06 -->
# AGENTS.md - WurstScript Map Project Notes

WurstScript Warcraft III map project notes for editing `.wurst` code, dependencies, generated objects, tests, or map build logic.

## Read On Demand

Keep this file as the baseline; load deeper material only when the task needs it:

- **Language semantics**: prefer the compiler-matched `~/.wurst/wurst-compiler/agent-docs/WURST_LANGUAGE.md`; otherwise use https://wurstlang.org/manual.html.
- **Stdlib APIs**: search `_build/dependencies/wurstStdlib2/wurst/` and read its `AGENTS.md` when present.
- **Other dependencies**: inspect their source and guidance under `_build/dependencies/<dep>/` before using or changing them.
- **Project conventions**: inspect nearby working code and project-local notes before choosing syntax, APIs, or style.

## Working Rules

- Edit source, configuration, and tests; never treat `_build/`, generated output, or downloaded dependencies as source-of-truth. Patch upstream dependencies at their source.
- Fix root causes with small, focused changes. Avoid duplicated branches, special-case workarounds, and unrelated refactors.
- Add narrow tests for changed behavior. Fix relevant compiler warnings unless a warning is intentionally suppressed and explained.
- Search declarations and existing usages instead of guessing APIs or signatures.

## Idiomatic Wurst

Write semantic Wurst, not translated JASS or hand-built data plumbing.

- Prefer stdlib and dependency APIs over raw `common.j`/`Blizzard.j` natives. Never use Blizzard `BJ` wrappers. Search first; use a native only when no maintained wrapper exists.
- Do not recreate timers, events, group enumeration, dummy casting, damage, effects, orders, or collections already covered by packages such as `ClosureTimers`, `ClosureEvents`, `ClosureForGroups`, `GroupUtils`, `DummyCaster`, `DamageEvent`, `Fx`, `Orders`, and the stdlib collections.
- String concatenation invokes `toString()` implicitly: write `"Kills: " + kills`; explicit `.toString()` there is redundant and warns.
- Use zero-overhead `vec2`/`vec3` tuples for coordinate values, not parallel reals/arrays or `location` handles.
- Use `ArrayList<T>` or another suitable stdlib collection for growable state, not a global array plus size, capacity, shifting, or removal bookkeeping. Reserve raw arrays for deliberately fixed-size or direct-indexed storage.
- New generic declarations always use colon syntax (`class Box<T:>`, `function map<T:>(...)`). Plain `<T>` declarations are deprecated; generic uses remain `Box<int>`.
- Prefer null-safe access (`?.`) when null means no-op: `findTarget()?.damage(50.)`. Use an explicit check when null needs handling, for assignment, or when a primitive-valued result cannot represent null.
- Prefer `let`, type inference, small functions, extension functions, cascades, and intentionally small public APIs. Every source belongs to a package; exports require `public`.

## Warcraft III Basics

- WC3 simulation is synchronized lockstep. Local UI, input, camera, and `GetLocalPlayer()` must remain presentation-only; never gate RNG, orders, object creation, or synchronized state on local data.
- Make ownership explicit. Destroy or release temporary effects, groups, locations, listeners, class instances, references, and owned collections through their stdlib lifecycle APIs. Avoid double-destroy and clear stale owner references.
- WC3 `int` is signed 32-bit and overflows silently. Promote before multiplication (`worth.toReal() * count`), not after the integer expression has overflowed.
- Closures capture locals by value; callback assignments do not update the outer local. Put shared mutable state on an owning class or use `reference(value)` and destroy it when finished.
- Use compiletime generation and stable ID helpers for object-editor data. Start generated objects from real melee bases and intentionally clear inherited abilities, costs, requirements, stock, food, race, art, sound, and tooltip fields that do not belong.

## Project And Backend

`wurst.build` is authoritative: `scriptMode` selects `LUA` or `JASS`; `wc3Patch` selects compatible core JASS and stdlib; `dependencies` are managed by `grill`; `buildMapData` controls output map metadata.

Read `scriptMode` before adding `execute()` or timer chunking. Lua has no practical operation limit, so timers should represent real asynchronous delay. Jass has a per-thread operation limit, so genuinely heavy work may require `execute()` or chunking. The installed WC3 client affects launch compatibility, not the build/typecheck target.

## Validate

```bash
grill install
grill typecheck --quiet
grill test --quiet
```

If a quiet check fails, rerun the smallest relevant target without `--quiet`. For map build changes, run `grill build ExampleMap.w3x --quiet`; add `--dev` only for behavior requiring `isProductionBuild() == false`.

Done means focused checks pass and relevant warnings are resolved or explained. Runtime/UI behavior that static checks cannot prove requires the smallest suitable WC3 or e2e verification.
