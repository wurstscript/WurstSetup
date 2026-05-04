# AGENTS.md — WurstScript Map Project Notes

This repository is a WurstScript Warcraft III map project. Use these notes when editing `.wurst` code, project dependencies, generated objects, or map logic.

## Project workflow

`grill` is the WurstScript dependency manager and project tool.

Use:

```bash
grill install
```

to install project dependencies.

Use:

```bash
grill install <https-git-url>
```

to add and pull a specific Git dependency.

After making Wurst changes, always run:

```bash
grill typecheck
```

Fix reported errors and relevant warnings before considering the task done.

## Project configuration (`wurst.build`)

`wurst.build` is a YAML file at the repository root that controls project metadata, dependencies, and map build settings. It is the source of truth for:

- `projectName` — human-readable project name
- `dependencies` — list of Git URLs managed by `grill`; add/remove entries here and run `grill install` to sync
- `buildMapData` — map metadata (name, fileName, author, scenario description, players, forces) written into the compiled `.w3x` file

Do not edit `_build/dependencies/` directly. Dependency source lives in those git repos; change `wurst.build` and reinstall to update.

## Lua vs Jass compilation

Warcraft III 1.32+ maps can target either **Lua** or **Jass** as the script runtime. Wurst compiles to whichever the map's script type is set to (configured in the World Editor map settings, not in `wurst.build`).

This distinction has practical consequences for how code should be written:

**Lua mode** (Reforged maps, recommended):
- No operation limit — long loops and deep call stacks are fine.
- `execute()` is a no-op for performance; do not add it as an op-limit workaround.
- Work that was split across multiple timer callbacks purely to avoid op-limits can be run synchronously.
- Periodic timers (`doPeriodically`, `doAfter`) are still async and introduce real frame delays — use them when you actually want a delay, not just to chunk work.

**Jass mode** (older maps):
- The Jass VM enforces an operation limit per thread (~1 million ops).
- `execute()` spawns a new thread, resetting the op counter — necessary for large loops.
- Heavy work must be split across ticks or use `execute()` wrappers.

When reading existing code, check which mode the map targets before adding or removing `execute()` calls or chunked timer patterns.

## Generated/build folders

The `_build/` folder contains dynamic generated content and must not be committed.

`_build/dependencies/` is managed by `grill`. Do not edit dependency code there as source-of-truth. The default dependency `wurstStdlib2` is the Wurst standard library.

## Coding style for this project

Prefer high-level Wurst and standard-library APIs over raw low-level Jass-style code.

For every `common.j` native there is usually a Wurst wrapper or extension function. Prefer the Wurst wrapper when available.

Prefer extension-function chaining and cascade syntax:

```wurst
unit
	..setOwner(player)
	..setPosition(pos)
	..addAbility(ABILITY_ID)
```

Avoid deeply nested call style:

```wurst
addAbility(setPosition(setOwner(unit, player), pos), ABILITY_ID) // avoid
```

Use `vec2` tuples for positions instead of `location` handles whenever possible. Locations are handles and should generally be avoided unless an API specifically requires them.

When generating object IDs, use compiletime expressions and ID generators so IDs stay consistent and collision-free. Do not hardcode new object IDs unless the surrounding code already intentionally does so.

## WurstScript essentials

All Wurst code must be inside a `package`.

```wurst
package MyPackage
import Printing

init
	print("Hello Wurst!")
```

Blocks are indentation-based. Use tabs or 4 spaces consistently; do not mix.

```wurst
if condition
	doSomething()
afterwards()
```

Newlines usually end statements, but can continue after `(`, `[`, operators, or before `.`, `..`, `)`, `]`, `begin`.

## Types and variables

Basic types: `boolean`, `int`, `real`, `string`.

Prefer `let` for immutable values, `var` for mutable values, and explicit types only when useful.

```wurst
let x = 5
var y = 10
y += 1
int z = 7
```

Arrays:

```wurst
int array a
int array b = [1, 2, 3]
constant NAMES = ["A", "B", "C"]
let len = b.length // initial length only
```

Package-level variables are globals. Locals can be declared anywhere inside functions.

## Functions

```wurst
function max(int a, int b) returns int
	if a > b
		return a
	return b

function printMax(int a, int b)
	print(max(a, b).toString())
```

Void functions omit `returns`. Do not use Jass-style `takes` / `returns nothing`.

## Control flow

```wurst
if x > y
	print("greater")
else if x < y
	print("smaller")
else
	print("equal")
```

```wurst
switch value
	case 1
		print("one")
	case 2 | 3
		print("two or three")
	default
		print("other")
```

Loops:

```wurst
while condition
	...

for i = 0 to 10
	...

for i = 10 downto 0
	...

for u in someGroup
	...

for u from someGroup
	...
```

Use `continue` to skip an iteration. `skip` is a no-op.

## Operators and expressions

Common operators: `+`, `-`, `*`, `/`, `div`, `%`, `mod`, `and`, `or`, `not`, `==`, `!=`, `<`, `<=`, `>`, `>=`.

Ternary:

```wurst
let label = n == 1 ? "enemy" : "enemies"
```

Casts and type checks:

```wurst
let i = obj castTo int
if obj instanceof SomeClass
	...
```

Avoid `castTo` where possible; casts are not runtime-checked.

## Cascade operator

`..` calls a method and returns the receiver, useful for fluent setup.

```wurst
CreateTrigger()
	..registerAnyUnitEvent(EVENT_PLAYER_UNIT_ISSUED_ORDER)
	..addCondition(Condition(function cond))
	..addAction(function action)
```

Prefer this for setup/configuration code.

## Packages

```wurst
package MyPackage
import SomePackage
import public ReExportedPackage
import initlater CyclicPackage
```

Package members are private by default. Use `public` to export.

```wurst
public function foo()
	...

public constant int SOME_ID = 'A000'
```

`import public` re-exports imported public names. `initlater` weakens initialization ordering and should only be used for unavoidable cycles.

Every package implicitly imports the standard `Wurst` package unless `NoWurst` is imported.

`init` blocks run at map start. Package initialization runs top-to-bottom; imported packages initialize before importers unless `initlater` is used.

## Classes

```wurst
class Caster
	unit u

	construct(real x, real y)
		u = createUnit(...)

	function cast(real x, real y)
		u.issueOrder(...)

	ondestroy
		u.kill()
```

Create/destroy:

```wurst
let c = new Caster(200., 400.)
c.cast(500., 30.)
destroy c
```

Objects created with `new` generally need `destroy`.

Class members are public by default. Use `private` or `protected` where needed.

Static members:

```wurst
class Terrain
	static var value = 12.
	static function getZ(real x, real y) returns real
		...

let z = Terrain.getZ(0., 0.)
```

## Inheritance

```wurst
class Missile
	construct(string fx, real x, real y)
		...

	function onCollide(unit u)

class FireballMissile extends Missile
	construct(real x, real y)
		super("Abilities\Weapons\RedDragonBreath\RedDragonMissile.mdl", x, y)

	override function onCollide(unit u)
		...
```

`super(...)` must be the first constructor statement. Overridden methods require `override`. Dynamic dispatch works through superclass references.

## Interfaces, modules, generics

Interface:

```wurst
interface Listener
	function onClick()
	function onAttack(real x, real y) returns boolean
```

Modules inject reusable behavior without subtype inheritance.

```wurst
module IntContainer
	int x

	public function getX() returns int
		return x

class C
	use IntContainer
```

Prefer new generics `T:` for performance-sensitive or instance-heavy generic containers:

```wurst
class Ref<T:>
	T value
```

Old `T` generics erase through integer casts and may share storage. New `T:` generics specialize per concrete type, often faster but may increase generated script size.

## Enums, tuples, extension functions

Enums are integer-backed named constants.

```wurst
enum Animal
	Sheep
	Dog
	Eagle
```

Tuples are value types and must not be destroyed.

```wurst
tuple vec2(real x, real y)
let p = vec2(1., 2.)
let q = p // copy
```

Add methods to existing types with extension functions:

```wurst
public function unit.getX() returns real
	return GetUnitX(this)

public function real.half() returns real
	return this / 2.

let x = u.getX().half()
```

## Lambdas and closures

Lambdas implement functional interfaces.

```wurst
interface Predicate<T>
	function isTrueFor(T t) returns boolean

Predicate<int> pred = x -> x mod 2 == 0
```

Standalone lambda type inference does not work:

```wurst
let pred = x -> x mod 2 == 0 // invalid
Predicate<int> pred = x -> x mod 2 == 0 // valid
```

Block lambda:

```wurst
doAfter(10.) ->
	u.kill()
	createNiceExplosion()
```

Closures capture locals by value. Closure objects generally need destruction if stored/allocated as objects. Inside a closure, `it` refers to the closure object itself.

A lambda can be used as `code` only if it has no parameters and captures no locals.

## Compiletime, object generation, tests

Compiletime functions:

```wurst
@compiletime function generateObjects()
	...
```

They have no parameters and no return value.

Compiletime expressions:

```wurst
let value = compiletime(fac(5))
```

Use compiletime object generation for object-editor data. Prefer object-editing wrappers and ID generators.

```wurst
@compiletime function createSpell()
	new AbilityDefinitionMountainKingThunderBolt(SPELL_ID)
		..setName("Wurst Bolt")
		..presetDamage(lvl -> 400. + lvl * 100.)
```

Tests:

```wurst
package MyTests
import Wurstunit

@Test public function multiplicationWorks()
	12.assertEquals(3 * 4)
```

Tests should be small, self-contained, and contain assertions.

## Formatting and naming

Naming:

* packages/classes: `UpperCamelCase`
* tuples: `lowerCamelCase`
* functions, members, locals: `lowerCamelCase`
* top-level constants: `UPPER_SNAKE_CASE`

Formatting:

* spaces around binary operators: `a + b`
* no space before call parentheses: `foo(1)`
* no spaces around `.` or `..`
* no spaces after `(` or `[` or before `)` or `]`
* use `// Comment`, with a space after `//`
* avoid manual horizontal alignment

Prefer:

* `let` over `var` when not mutated
* local declarations near first use
* type inference when obvious
* polymorphism over `instanceof` / `typeId`
* extension functions for readable APIs
* normal `for` loops for side-effect iteration
* fixing compiler warnings; prefix intentionally unused variables with `_`

Hot doc comments:

```wurst
/** This appears in autocomplete. */
```

## Common pitfalls

* Always run `grill typecheck` after code changes.
* Do not commit `_build/` or edit `_build/dependencies/` as source.
* Wurst code must be inside `package`.
* Indentation defines blocks.
* Package members are private unless `public`.
* Class members are public unless restricted.
* `import` does not re-export; use `import public`.
* Avoid `initlater` unless necessary.
* `array.length` is only the initial length.
* `new` objects and closure objects often need `destroy`.
* Tuples are value copies and must not be destroyed.
* Prefer `vec2` over `location` handles.
* `castTo` is unchecked.
* Lambda target type must be known.
* Lambdas used as `code` cannot capture locals.
* Varargs are limited by Jass’s 31-argument limit.
* Prefer `T:` generics for performance-sensitive containers.
