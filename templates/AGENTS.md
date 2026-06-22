<!-- WURST_AGENTS_TEMPLATE_VERSION: 2026-06-22 -->
# AGENTS.md - WurstScript Map Project Notes

WurstScript Warcraft III map project notes for editing `.wurst` code, dependencies, generated objects, tests, or map build logic.

## Working Rules

- Prefer simple, maintainable code. Fix root causes; avoid brittle workarounds, duplicated branches, and special-case patches.
- Keep packages focused and below ~500 lines; split by feature, responsibility, or data type.
- Make changes in the source package, not generated output. Do not edit `_build/` or `_build/dependencies/` as source-of-truth.
- Use Wurst stdlib/library APIs and project helpers; never call a raw `common.j`/Jass native when a wrapper exists, and do not reinvent what stdlib already provides. See **Stdlib-First** below.
- When unsure about Wurst syntax or local APIs, inspect nearby working code before guessing.
- Keep tests narrow. Add/update tests for behavior, parsing, compiletime generation, or shared utilities.
- Avoid broad refactors unless they directly reduce risk or complexity for the requested change.

## Stdlib-First: No Raw JASS Natives (Mandatory)

The most important coding rule: high-level Wurst packages must use the WurstScript stdlib and library APIs, never ported JASS. The goal is clean, reusable Wurst — not a JASS transliteration.

- Never call a raw `common.j` / `Blizzard.j` native when a Wurst wrapper or extension function exists. There is one for almost every native (on `unit`, `player`, `group`, `string`, `rect`, ...). Grep the stdlib (`_build/dependencies/wurstStdlib2/wurst/`) before writing a native call.
- The only bar for a raw native is that you searched and confirmed no wrapper exists — then add a one-line comment saying so.
- "It compiles" is not enough. Code that reads like JASS (manual handle juggling, native calls, global trigger callbacks, op-limit chunking) is wrong here; rewrite it idiomatically.

Use the stdlib API, not a raw native, for at least:

- Timers → `ClosureTimers` (`doAfter`, `doPeriodically`); never `CreateTimer`/`TimerStart`/`PauseTimer`/`DestroyTimer`.
- Printing → `print` / `printTimed` / `p.print`; never `DisplayText*ToPlayer`/`...ToForce`.
- Player state → `Player` extensions (`p.addGold`, `p.getGold`, `p.getId`, ...); prefer the `players[i]` array over `Player(i)`.
- Unit inspection → `Unit` extensions (`u.getTypeId()`, `u.getOwner()`, `u.getAbilityLevel(id)`, ...).
- Hashtables → `Hashtable` extensions (`ht.saveInt`/`loadInt`/`flushChild`/...).
- Group iteration → `ClosureForGroups` (`forUnitsInRange`, `forUnitsInRect`) + `GroupUtils` (`getGroup()` / `group.release()`), not `GroupEnum*` + `ForGroup` globals.

The `CreateTrigger()..register...()..addAction() ->` cascade is the accepted idiom and is fine.

### Do Not Reinvent Stdlib Infrastructure (Mandatory)

Keep custom engine-level infrastructure to a minimum. Stdlib packages are battle-tested and handle the WC3 edge cases (recycling, op-limits, cleanup, desync) that hand-rolled versions get wrong. Grep for an existing system before building one; do not ship a parallel implementation of something stdlib provides:

- Dummy spell casting → `DummyCaster` / `InstantDummyCaster` (unit pooling: `DummyRecycler`).
- Triggered damage → `DummyDamage` to deal, `DamageEvent` to detect/modify.
- Events → `ClosureEvents` (`EventListener.add(...)`) / `RegisterEvents`; no custom global-trigger dispatcher or event bus.
- Knockback / FX / sound / interpolation / orders → `Knockback3`, `Fx`, `SoundUtils`/`Sounds`, `Interpolation`, `Orders`/`OrderStringFactory`.
- Collections → `LinkedList`, `HashMap`, `HashList`.

If stdlib almost fits, prefer a thin wrapper around the stdlib type over a from-scratch system and note why in a comment. Reinventing this is treated as a defect even if it compiles and passes tests, because it reintroduces solved bugs.

## Agent Workflow

Install dependencies:

```bash
grill install
```

After Wurst changes, run quiet checks first:

```bash
grill typecheck --quiet
grill test --quiet
```

If quiet output reports a failure, rerun narrowly:

```bash
grill typecheck
grill test PackageOrTestName
```

Use the failed file, line, package, or test name to narrow the next command. Avoid full noisy reruns unless there is no target.

For build changes:

```bash
grill build ExampleMap.w3x --quiet
```

Builds default to production mode, so compiletime `isProductionBuild()` returns `true`. Use `grill build ExampleMap.w3x --dev --quiet` only when validating run/development-mode behavior where `isProductionBuild()` must be `false`; `typecheck` and `test` do not need this flag.

Done means relevant errors/warnings are fixed or explicitly explained.

## Project Configuration

`wurst.build` is the root YAML config. Key fields: `projectName`, `dependencies` (Git URLs managed by `grill`), and `buildMapData` (metadata written to the output `.w3x`). The default dependency is usually `wurstStdlib2`. Patch upstream repos instead of editing copied dependency code.

## Lua vs Jass

Maps target Lua or Jass via World Editor settings.

Lua mode:

- No practical op-limit; long loops and deep calls are okay.
- `execute()` is a no-op for performance. Do not add it as an op-limit workaround.
- Use timers only when you need real asynchronous delay.

Jass mode:

- The VM has an operation limit per thread.
- `execute()` resets the op counter by starting a new thread.
- Heavy work may need chunking across ticks.

Check the target before adding/removing `execute()` or timer chunking.

## Wurst Essentials

Every `.wurst` file starts with a package:

```wurst
package MyPackage
import Wurstunit

init
	print("loaded")
```

Blocks are indentation-based. Use tabs or 4 spaces consistently; do not mix.

Common declarations:

```wurst
let immutable = 5
var mutable = 10
constant int SOME_ID = 'A000'
int array values = [1, 2, 3]

function max(int a, int b) returns int
	if a > b
		return a
	return b

function doThing()
	print("void functions omit returns")
```

Use `let` unless mutation is needed. Put locals near first use. Prefer obvious type inference. Do not write Jass-style `takes` / `returns nothing`.

Control flow:

```wurst
if x > y
	...
else if x < y
	...

switch kind
	case 1
		...
	default
		...

while keepGoing
	...

for i = 0 to 10
	...

for i = 10 downto 0
	...

for u in group
	...

for u from group
	...
```

`continue` skips an iteration; `skip` is a no-op. Statements usually end at newline. Continue after `(`, `[`, operators, or before `.`, `..`, `)`, `]`, `begin`.

Common operators: `+`, `-`, `*`, `/`, `div`, `%`, `mod`, `and`, `or`, `not`, `==`, `!=`, `<`, `<=`, `>`, `>=`.

```wurst
let label = count == 1 ? "unit" : "units"
```

## WurstScript Production Pitfalls

These are recurring real-world Wurst/Warcraft III failure modes. Treat this section as a pre-edit checklist for any non-trivial Wurst change.

### Integer overflow

WC3 `int` is 32-bit signed and wraps silently at ~2.1 billion (`2^31 - 1`) — no exception, just a negative/garbage value that poisons every downstream comparison and division. Easy to hit when multiplying or summing large game quantities (gold/worth, army totals, damage products, accumulated stats).

- Promote to `real` BEFORE multiplying two large quantities: `a.toReal() * b`, never `(a * b).toReal()` (the latter already overflowed).
- Same for running sums of products: `total += worth.toReal() * count * mult`.
- Watch `worth * worth`, `count * worth`, `total * total` in scoring/stats; aggregate worths routinely exceed ~46k (the square root of int-max), so their product overflows in ordinary large games.
- Wurst `/` is real division even for two ints (use `div` for integer division), so division itself does not overflow — but its operands still can. Prefer `real` for any accumulator that fans in many large terms.

### Closure capture is by value

Wurst closures capture locals by value. If a closure assigns to a local from an outer scope, the outer local is not updated.

Bug pattern:

```wurst
framehandle clicked = null
dialog.build() ->
	clicked = textButton("OK", 0.08, 0.024)
clicked.onClick() -> // clicked is still null outside the build closure
	doThing()
```

Safer pattern:

```wurst
dialog.build() ->
	let clicked = textButton("OK", 0.08, 0.024)
	clicked.onClick() ->
		doThing()
```

Use `reference(value)` only when a value really must be read or mutated across closure boundaries, and destroy the reference when the owner is done with it:

```wurst
let clickedRef = reference(null)
dialog.build() ->
	clickedRef.val = textButton("OK", 0.08, 0.024)
clickedRef.val.onClick() ->
	doThing()
destroy clickedRef
```

Prefer avoiding the cross-boundary mutable reference entirely when the handler can be registered inside the closure that creates the frame.

### Object generation base IDs carry baggage

Generated object-editor definitions must use real Warcraft III melee objects as base objects, not custom objects generated elsewhere in the map. Custom-object bases can compile into invalid or order-dependent object data.

Because melee bases carry their own fields, always audit and intentionally clear inherited side effects when creating a generated unit, building, ability, upgrade, or item. Common inherited baggage includes:

- repair gold/lumber costs and repair time
- melee upgrades used / researches available / tech requirements
- stock, dependency, bounty, collision, food, race, target, and classification fields
- default abilities, autocast/order strings, buffs, art, missile, sound, and tooltip fields

Prefer local helper presets that explicitly null known-dangerous inherited fields for each object family, then layer the intended fields afterwards. Regression tests for generated object config should assert the absence of known inherited side effects, not only the presence of the new feature.

### Wurst object lifetime is manual

Lua output is garbage-collected at the runtime level, but Wurst class lifetimes and destructors are still explicit. Objects created with `new`, closure/listener objects, timers/callbacks, references, collections, layout reports, and many helper wrappers usually need `destroy` when their owner is done.

Do not rely on "Lua will GC it" if an `ondestroy` cleans up important state, callbacks, frame listeners, arrays, or nested objects. Conversely, do not double-destroy. Wurst instance ids can be reused, so a stale reference may point at a different future object and there is no reliable generic "is this destroyed?" check. Owners must clear stale references themselves after destroy:

```wurst
if watcher != null
	destroy watcher
	watcher = null
```

### Table UI and layout dependencies

If a project uses `wurst-table-layout` / `TableUi`, read that dependency's `AGENTS.md`, `AI_USAGE.md`, and `WC3_FRAMEHANDLE_GUIDE.md` before editing UI. Prefer the provided helpers over raw frame code.

- Load TOC files in `init` when needed, but do not create, move, size, show/hide, reparent, or otherwise manipulate custom frames during blocking map-load init. Delay actual frame work with `doAfter(0.)` or later.
- Build frames under their eventual parent (`withParent(...)` or `dialogFrame(...).build() ->`) rather than creating under a global parent and re-parenting later; WC3 can desync visual and clickable areas after `setParent`.
- Keep root panels, dialogs, dropdowns, and sidecars in the 4:3 safe band with `placeSafe(...)` and declared dimensions. Do not size or place UI from `BlzGetLocalClientWidth()` / `BlzGetLocalClientHeight()` unless guarded against zero/invalid values; minimized clients can report unusable dimensions.
- Avoid on-demand complex frame creation during gameplay when players may be alt-tabbed/minimized. Prefer creating reusable hidden frame trees after map load, then only owner-show/owner-hide/update them.
- Do not move Blizzard default chat/message frames with arbitrary sizes/coords to make room for custom UI. Bad coordinates and default-frame refreshes can crash/desync; create map-owned UI in a safe area instead.
- Register button handlers inside the same build callback that creates the button, or pass the button into a helper immediately. Do not assign a button/frame to an outer local inside a build callback and call `.onClick()` on that outer local afterwards.
- Prefer table-wide defaults for repeated alignment, such as `layout.defaultHalign(Align.CENTER)`, instead of writing `..center()` on every row. Use per-row alignment calls only for exceptions.
- Hide and reuse multiplayer UI frame trees. Do not destroy/recreate framehandles during gameplay cleanup.
## Packages and API Shape

- Package members are private by default; use `public` for exports.
- Class members are public by default; restrict with `private`/`protected`.
- Every package implicitly imports `Wurst` unless `NoWurst` is imported.
- `import public` re-exports names. Plain `import` does not.
- Avoid `initlater` unless breaking an unavoidable init cycle.
- Package initialization is top-to-bottom; imports initialize before importers.

Naming:

- packages/classes: `UpperCamelCase`
- tuples: `lowerCamelCase`
- functions/members/locals: `lowerCamelCase`
- top-level constants: `UPPER_SNAKE_CASE`

## Preferred Wurst Style

Use cascade syntax for setup:

```wurst
CreateTrigger()
	..registerAnyUnitEvent(EVENT_PLAYER_UNIT_ISSUED_ORDER)
	..addCondition(Condition(function cond))
	..addAction(function action)
```

Use extension functions for readable APIs:

```wurst
public function unit.getX2() returns real
	return GetUnitX(this)
```

Prefer `vec2` tuples over `location` handles unless required. Prefer polymorphism/data modeling over large `instanceof`/`typeId` chains. Avoid unchecked `castTo` unless proven safe.

Lambdas need a target type. Standalone inference does not work:

```wurst
Predicate<int> even = x -> x mod 2 == 0

doAfter(1.) ->
	print("later")
```

Closures capture locals by value. Stored/object-backed closures often need cleanup. Use `reference(...)` for intentional cross-closure mutation, and destroy the reference when finished. Lambdas used as `code` cannot take parameters or capture locals.

## Classes, Tuples, Generics

`new` objects generally need `destroy`. Tuples are value types and must not be destroyed.

```wurst
class Missile
	function onCollide(unit u)

class Fireball extends Missile
	override function onCollide(unit u)
		...
```

`super(...)` must be the first constructor statement. Overridden methods require `override`.

Interfaces declare required methods; modules inject reusable members:

```wurst
interface Listener
	function onClick()

module HasOwner
	player owner

class Button
	use HasOwner
```

Prefer `T:` generics for performance-sensitive or instance-heavy containers:

```wurst
class Box<T:>
	T value
```

Old `T` generics erase through integer casts and can share storage.

## Compiletime and Objects

Use compiletime generation for object-editor data. Prefer wrappers and ID generators so IDs stay stable and collision-free. Generated objects must use melee base objects, then explicitly clear inherited fields that would create unwanted side effects.

```wurst
let value = compiletime(fac(5))

@compiletime function createSpell()
	new AbilityDefinitionMountainKingThunderBolt(SPELL_ID)
		..setName("Wurst Bolt")
		..presetDamage(lvl -> 400. + lvl * 100.)
```

Avoid hardcoded new object IDs unless the existing code intentionally does so.

## Tests

```wurst
package MyTests
import Wurstunit

@Test public function multiplicationWorks()
	12.assertEquals(3 * 4)
```

Tests should be small, deterministic, self-contained, and assertion-driven. If quiet output lists a failed package/test, rerun that target before expanding scope.

## Formatting

- spaces around binary operators: `a + b`
- no space before call parentheses: `foo(1)`
- no spaces around `.` or `..`
- no spaces after `(` or `[` or before `)` or `]`
- comments use `// Comment`
- avoid manual horizontal alignment
- prefix intentionally unused variables with `_`

Hot doc comments:

```wurst
/** This appears in autocomplete. */
```

## Pitfalls

- Wurst code must be inside `package`.
- Indentation defines blocks.
- `array.length` is only the initial length.
- `new` objects and stored closure objects often need `destroy`.
- Lambdas need a known target type.
- Lambdas used as `code` cannot capture locals.
- Varargs are limited by Jass's 31-argument limit.
- Fix compiler warnings unless they are intentionally suppressed.
