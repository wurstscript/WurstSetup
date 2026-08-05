<!-- WURST_AGENTS_TEMPLATE_VERSION: 2026-07-16 -->
# AGENTS.md - WurstScript Map Project Notes

WurstScript Warcraft III map project notes for editing `.wurst` code, dependencies, generated objects, tests, or map build logic.

## Read More On Demand

This file is the working set; pull deeper docs into context only when the task needs them:

- **Stdlib APIs**: grep `_build/dependencies/wurstStdlib2/wurst/` for wrappers and packages before writing a native call or new infrastructure.
- **Dependency guides**: before editing code that uses a dependency, check `_build/dependencies/<dep>/` for its own `AGENTS.md` or usage guides and read them first (e.g. `wurst-table-layout` ships `AGENTS.md`, `AI_USAGE.md`, and `WC3_FRAMEHANDLE_GUIDE.md` — required reading before UI work).
- **Language details**: https://wurstlang.org/manual.html (full manual: generics, closures, modules, compiletime, operators).
- When unsure about syntax or local APIs, inspect nearby working code before guessing.

## Working Rules

- Prefer simple, maintainable code. Fix root causes; avoid brittle workarounds, duplicated branches, and special-case patches.
- Keep packages focused and below ~500 lines; split by feature, responsibility, or data type.
- Make changes in the source package, not generated output. Do not edit `_build/` as source-of-truth; patch upstream dependency repos instead of copied dependency code.
- Keep tests narrow. Add/update tests for behavior, parsing, compiletime generation, or shared utilities.
- Avoid broad refactors unless they directly reduce risk or complexity for the requested change.
- Fix compiler warnings unless they are intentionally suppressed.

## Stdlib-First: No Raw JASS Natives (Mandatory)

The most important coding rule: use the WurstScript stdlib and library APIs, never ported JASS. The goal is clean, reusable Wurst — not a JASS transliteration. Never call a raw `common.j`/`Blizzard.j` native when a wrapper or extension function exists (there is one for almost every native); grep the stdlib first. The only bar for a raw native is that you searched and confirmed no wrapper exists — then add a one-line comment saying so. Code that reads like JASS (manual handle juggling, native calls, global trigger callbacks, op-limit chunking) is wrong here even if it compiles.

Use the stdlib API, not a raw native, for at least:

- Timers → `ClosureTimers` (`doAfter`, `doPeriodically`); never `CreateTimer`/`TimerStart`/`PauseTimer`/`DestroyTimer`.
- Printing → `print` / `printTimed` / `p.print`; never `DisplayText*ToPlayer`/`...ToForce`.
- Player state → `Player` extensions (`p.addGold`, `p.getId`, ...); prefer `players[i]` over `Player(i)`.
- Unit inspection → `Unit` extensions (`u.getTypeId()`, `u.getOwner()`, `u.getAbilityLevel(id)`, ...).
- Hashtables → `Hashtable` extensions (`ht.saveInt`/`loadInt`/`flushChild`/...).
- Group iteration → `ClosureForGroups` (`forUnitsInRange`, `forUnitsInRect`) + `GroupUtils` (`getGroup()`/`group.release()`), not `GroupEnum*` + `ForGroup` globals.

The `CreateTrigger()..register...()..addAction() ->` cascade is the accepted idiom and is fine.

Likewise, do not reinvent stdlib infrastructure — it is battle-tested against WC3 edge cases (recycling, op-limits, cleanup, desync) that hand-rolled versions get wrong. Grep for an existing system before building one:

- Dummy spell casting → `DummyCaster` / `InstantDummyCaster` (unit pooling: `DummyRecycler`).
- Triggered damage → `DummyDamage` to deal, `DamageEvent` to detect/modify.
- Events → `ClosureEvents` (`EventListener.add(...)`) / `RegisterEvents`; no custom global-trigger dispatcher or event bus.
- Knockback / FX / sound / interpolation / orders → `Knockback3`, `Fx`, `SoundUtils`/`Sounds`, `Interpolation`, `Orders`/`OrderStringFactory`.
- Collections → `LinkedList`, `HashMap`, `HashList`.

If stdlib almost fits, wrap the stdlib type thinly and note why in a comment. Reinventing this is treated as a defect even if tests pass.

## Agent Workflow

```bash
grill install                 # install/update dependencies
grill typecheck --quiet       # after Wurst changes
grill test --quiet
```

If quiet output reports a failure, rerun narrowly using the failed file, line, package, or test name (`grill typecheck`, `grill test PackageOrTestName`). Avoid full noisy reruns unless there is no target.

For build changes: `grill build ExampleMap.w3x --quiet`. Builds default to production mode (compiletime `isProductionBuild()` returns `true`); add `--dev` only when validating behavior that needs `isProductionBuild() == false`. To dump a map's object-editor data to Wurst source: `grill exportobjects <mapfile|folder>`.

Done means relevant errors/warnings are fixed or explicitly explained.

## Project Configuration

`wurst.build` is the root YAML config. Key fields: `projectName`, `dependencies` (Git URLs managed by `grill`), and `buildMapData` (metadata written to the output `.w3x`). The default dependency is usually `wurstStdlib2`.

## Lua vs Jass

Maps target Lua or Jass via World Editor settings. Check the target before adding/removing `execute()` or timer chunking:

- **Lua**: no practical op-limit; long loops and deep calls are fine. Do not add `execute()` as an op-limit workaround. Use timers only for real asynchronous delay.
- **Jass**: the VM has an operation limit per thread; `execute()` resets it by starting a new thread. Heavy work may need chunking across ticks.

## Wurst Essentials

Every `.wurst` file starts with a package; blocks are indentation-based (tabs or 4 spaces, never mixed):

```wurst
package MyPackage
import Wurstunit

init
	print("loaded")
```

```wurst
let immutable = 5
var mutable = 10
constant int SOME_ID = 'A000'
int array values = [1, 2, 3]

function max(int a, int b) returns int
	if a > b
		return a
	return b
```

Use `let` unless mutation is needed. Put locals near first use. Prefer type inference. Do not write Jass-style `takes` / `returns nothing`.

Control flow: `if`/`else if`/`else`, `switch x` + `case`/`default`, `while`, `for i = 0 to 10`, `for i = 10 downto 0`, `for u in group` / `for u from group`. `continue` skips an iteration; `skip` is a no-op statement. Statements end at newline; continue after `(`, `[`, operators, or before `.`, `..`, `)`, `]`, `begin`.

Operators: `+`, `-`, `*`, `/` (real division, even on two ints), `div` (integer division), `%`, `mod`, `and`, `or`, `not`, `==`, `!=`, `<`, `<=`, `>`, `>=`, ternary `cond ? a : b`.

Null-safe member access with `?.` skips the access (including argument evaluation) when the receiver is null; the receiver is evaluated once:

```wurst
target?.kill()                  // no-op when target is null
let owner = target?.getOwner()  // null when target is null; chains: a?.next?.next
```

The receiver type must be nullable (class/interface/string/handle — not `int`/`real`/`boolean`). If the member's own type cannot represent null (e.g. `getCount()` returning `int`), the `?.` call is only valid as a standalone statement, not as a value — use an explicit `if x != null` there. `?.` is not assignable (`a?.x = 5` is invalid).

## Packages and API Shape

- Package members are private by default; use `public` for exports. Class members are public by default; restrict with `private`/`protected`.
- Every package implicitly imports `Wurst` unless `NoWurst` is imported. `import public` re-exports names; plain `import` does not.
- Package initialization is top-to-bottom; imports initialize before importers. Avoid `initlater` unless breaking an unavoidable init cycle.

Naming: packages/classes `UpperCamelCase`; tuples, functions, members, locals `lowerCamelCase`; top-level constants `UPPER_SNAKE_CASE`.

## Preferred Wurst Style

Use cascade syntax for setup and extension functions for readable APIs:

```wurst
CreateTrigger()
	..registerAnyUnitEvent(EVENT_PLAYER_UNIT_ISSUED_ORDER)
	..addCondition(Condition(function cond))
	..addAction(function action)

public function unit.getX2() returns real
	return GetUnitX(this)
```

Prefer `target?.damage(50.)` over `if target != null` + `target.damage(50.)` when the null case simply does nothing; keep the explicit check when the null case needs handling or the accessed value's type cannot be null.

Prefer `vec2` tuples over `location` handles unless required. Prefer polymorphism/data modeling over large `instanceof`/`typeId` chains. Avoid unchecked `castTo` unless proven safe.

Lambdas need a target type — standalone inference does not work:

```wurst
Predicate<int> even = x -> x mod 2 == 0

doAfter(1.) ->
	print("later")
```

Lambdas used as `code` cannot take parameters or capture locals.

## Classes, Tuples, Generics

`new` objects generally need `destroy`. Tuples are value types and must not be destroyed. `super(...)` must be the first constructor statement; overridden methods require `override`. Interfaces declare required methods; modules (`use`) inject reusable members.

Prefer `T:` generics for performance-sensitive or instance-heavy containers (`class Box<T:>`); old `T` generics erase through integer casts and can share storage.

## Compiletime and Objects

Use compiletime generation for object-editor data. Prefer wrappers and ID generators so IDs stay stable and collision-free; avoid hardcoded new object IDs unless existing code intentionally does so.

```wurst
let value = compiletime(fac(5))

@compiletime function createSpell()
	new AbilityDefinitionMountainKingThunderBolt(SPELL_ID)
		..setName("Wurst Bolt")
		..presetDamage(lvl -> 400. + lvl * 100.)
```

Generated objects must use real melee objects as bases, never other custom objects (custom bases compile into invalid or order-dependent data). Melee bases carry baggage — repair costs, upgrades/tech requirements, stock/bounty/food/race/classification fields, default abilities, art/sound/tooltips — so audit and explicitly clear inherited side effects per object family (prefer local helper presets that null known-dangerous fields, then layer intended fields). Regression tests for generated objects should assert the *absence* of known inherited side effects, not only the presence of new fields.

## Production Pitfalls

Recurring real-world failure modes; treat as a pre-edit checklist for non-trivial changes.

### Integer overflow

WC3 `int` is 32-bit signed and wraps silently at ~2.1 billion. Easy to hit when multiplying or summing large game quantities (gold/worth totals, damage products, accumulated stats — aggregate worths routinely exceed ~46k, the square root of int-max).

- Promote to `real` BEFORE multiplying: `a.toReal() * b`, never `(a * b).toReal()` (already overflowed).
- Same for running sums of products: `total += worth.toReal() * count * mult`.
- `/` is real division so it does not overflow, but its operands still can. Prefer `real` accumulators that fan in many large terms.

### Closure capture is by value

If a closure assigns to a local from an outer scope, the outer local is not updated. Do not assign a value to an outer local inside a callback and use that outer local afterwards — declare it inside the closure, or register follow-up handlers inside the same callback that creates the value:

```wurst
// BUG: clicked is still null outside the build closure
framehandle clicked = null
dialog.build() ->
	clicked = textButton("OK", 0.08, 0.024)
clicked.onClick() ->
	doThing()

// OK: keep creation and handler in the same closure
dialog.build() ->
	let clicked = textButton("OK", 0.08, 0.024)
	clicked.onClick() ->
		doThing()
```

When a value genuinely must cross closure boundaries, use `reference(value)`, access `.val`, and `destroy` the reference when the owner is done — but prefer restructuring to avoid it.

### Wurst object lifetime is manual

Lua output is garbage-collected at the runtime level, but Wurst class lifetimes and destructors are still explicit. Objects created with `new`, stored closures/listeners, timers/callbacks, references, and collections usually need `destroy` when their owner is done — do not rely on "Lua will GC it" if an `ondestroy` cleans up state, callbacks, or nested objects. Conversely, do not double-destroy: instance ids can be reused and there is no generic "is destroyed?" check, so owners must null their own stale references:

```wurst
if watcher != null
	destroy watcher
	watcher = null
```

### Custom UI work

If the project uses `wurst-table-layout` / `TableUi`, read that dependency's `AGENTS.md`, `AI_USAGE.md`, and `WC3_FRAMEHANDLE_GUIDE.md` before editing UI (see Read More On Demand). Hard rules that hold regardless:

- Load TOC files in `init` if needed, but do no actual frame work (create/move/size/show/reparent) during blocking map-load init — delay it with `doAfter(0.)` or later.
- Build frames under their eventual parent (`withParent(...)` or inside `dialogFrame(...).build() ->`); re-parenting after creation can desync visual and clickable areas.
- Keep root panels/dialogs in the 4:3 safe band with `placeSafe(...)` and declared dimensions. Never size/place UI from `BlzGetLocalClientWidth()/Height()` without guarding against zero/invalid values (minimized clients).
- Prefer building reusable hidden frame trees after map load, then show/hide/update them; do not create complex frames on demand mid-game or destroy/recreate framehandles during cleanup.
- Do not move or resize Blizzard default frames (chat/messages) to make room — bad coordinates and default-frame refreshes can crash/desync.
- Prefer table-wide defaults (e.g. `layout.defaultHalign(Align.CENTER)`) over per-row alignment calls.

## Tests

```wurst
package MyTests
import Wurstunit

@Test public function multiplicationWorks()
	12.assertEquals(3 * 4)
```

Tests should be small, deterministic, self-contained, and assertion-driven. If quiet output lists a failed package/test, rerun that target before expanding scope.

## Formatting

- spaces around binary operators: `a + b`; no space before call parentheses: `foo(1)`
- no spaces around `.`, `..` or `?.`; no spaces after `(`/`[` or before `)`/`]`
- comments use `// Comment`; doc comments `/** ... */` appear in autocomplete
- avoid manual horizontal alignment; prefix intentionally unused variables with `_`

## Quick Pitfall Checklist

- Wurst code must be inside `package`; indentation defines blocks.
- `array.length` is only the initial length.
- Varargs are limited by Jass's 31-argument limit.
- Lambdas need a known target type; `code` lambdas cannot capture locals.
- `new` objects and stored closures usually need `destroy` (see Production Pitfalls).
