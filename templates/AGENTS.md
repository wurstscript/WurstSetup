# AGENTS.md - WurstScript Map Project Notes

WurstScript Warcraft III map project notes for editing `.wurst` code, dependencies, generated objects, tests, or map build logic.

## Working Rules

- Prefer simple, maintainable code. Fix root causes; avoid brittle workarounds, duplicated branches, and special-case patches.
- Keep packages focused and below ~500 lines; split by feature, responsibility, or data type.
- Make changes in the source package, not generated output. Do not edit `_build/` or `_build/dependencies/` as source-of-truth.
- Prefer Wurst standard-library wrappers and project helpers over raw `common.j`/Jass-style calls.
- When unsure about Wurst syntax or local APIs, inspect nearby working code before guessing.
- Keep tests narrow. Add/update tests for behavior, parsing, compiletime generation, or shared utilities.
- Avoid broad refactors unless they directly reduce risk or complexity for the requested change.

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

Closures capture locals by value. Stored/object-backed closures often need cleanup. Lambdas used as `code` cannot take parameters or capture locals.

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

Use compiletime generation for object-editor data. Prefer wrappers and ID generators so IDs stay stable and collision-free.

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
