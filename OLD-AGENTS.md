# AGENTS.md

## 1. Project Purpose

This repository is a Minecraft mod development project.

The highest priority is **version-correct implementation**.

Minecraft, Forge, NeoForge, Fabric, mappings, Java versions, Gradle plugins, and related APIs can differ significantly between versions.

Never assume that an API remembered from another Minecraft version or mod loader is valid for this project.

---

## 2. Source of Truth

Before modifying or generating code, determine the actual project environment.

Inspect these files when available:

- `gradle.properties`
- `build.gradle`
- `build.gradle.kts`
- `settings.gradle`
- `settings.gradle.kts`
- `libs.versions.toml`
- `fabric.mod.json`
- `neoforge.mods.toml`
- `mods.toml`
- `net.minecraft:minecraft-merged`
- existing source files
- existing imports
- Gradle dependency declarations

These project files are the source of truth.

Determine, when applicable:

- Minecraft version
- mod loader
- mod loader version
- mappings
- Java version
- Gradle version
- modding plugin version
- dependency versions
- relevant third-party library versions

Do not guess any of these values.

If documentation, memory, examples, or existing assumptions conflict with the project's actual dependency configuration, follow the project configuration.

---

## 3. Minecraft Version Safety

Minecraft APIs are highly version-sensitive.

Code that works in one version may fail in another version because of:

- renamed classes
- renamed methods
- changed constructors
- changed method parameters
- changed return types
- moved packages
- changed registries
- changed events
- changed networking systems
- changed rendering systems
- changed data components
- changed resource systems
- changed mappings
- removed APIs
- newly introduced APIs

Therefore:

**NEVER rely only on remembered Minecraft APIs.**

Before using a version-sensitive API, verify that it exists in the version used by this project.

---

## 4. Mod Loader Safety

Never mix APIs from different mod loaders.

Possible environments include:

- NeoForge
- Forge
- Fabric
- Quilt
- other loaders

If the project uses NeoForge:

- do not use Forge APIs unless compatibility is explicitly verified
- do not use Fabric APIs

If the project uses Forge:

- do not assume modern NeoForge APIs are available
- do not use Fabric APIs

If the project uses Fabric:

- do not use Forge or NeoForge APIs
- verify Fabric API version compatibility

Never silently convert examples from another loader.

First identify the actual loader used by the project.

---

## 5. API Verification Rules

Before using any unfamiliar or version-sensitive:

- class
- interface
- method
- constructor
- field
- annotation
- event
- registry
- package
- networking API
- rendering API
- command API
- data API

verify it against the current project.

Use the following priority order:

1. Current project source code
2. Current project's resolved dependencies
3. Attached dependency source code
4. Locally available Minecraft / loader source
5. Documentation stored in this repository
6. Official documentation for the exact relevant version
7. Trusted examples targeting the exact same version

Memory should not be treated as authoritative API documentation.

---

## 6. Search Before Guessing

If an API is uncertain, search for it.

Do not invent:

- class names
- package names
- method names
- event names
- registry names
- constructor signatures
- mappings
- annotations

If a referenced API cannot be found, assume it may be:

- renamed
- moved
- removed
- loader-specific
- mapping-specific
- version-specific

Search the current dependencies or source to find the correct equivalent.

Do not repeatedly guess different method names until something happens to compile.

---

## 7. Existing Code First

Before implementing a feature, inspect the repository for similar existing implementations.

Search for relevant:

- registrations
- blocks
- items
- entities
- menus
- screens
- events
- networking
- capabilities
- attachments
- data components
- commands
- rendering
- configuration
- world generation
- saved data
- serialization

Prefer patterns already used successfully in this project.

Maintain consistency with the project's existing architecture unless there is a clear reason to change it.

---

## 8. Dependency Inspection

When an API is unclear, inspect the actual dependency rather than guessing.

Useful sources may include:

- Gradle dependency cache
- attached source JARs
- decompiled dependency classes
- generated Minecraft sources
- IDE-resolved dependencies
- loader source code

When possible, inspect the exact class definition and method signature.

Before calling something such as:

```java
SomeClass.someMethod(...);
```

verify:

- `SomeClass` exists
- the package is correct
- `someMethod` exists
- the method is accessible
- parameters match
- generic types match
- return type matches
- the API exists in the current version

---

## 9. Mapping Awareness

Minecraft names may differ between mapping systems.

Possible mapping environments include:

- Mojang mappings
- Yarn
- intermediary mappings
- legacy MCP-style mappings
- loader-specific mapping layers

Do not assume a name found online matches this project.

Always identify the mapping environment before copying API names.

When converting examples between mappings, verify every affected class and method.

---

## 10. Required Implementation Workflow

For every significant implementation task, follow this workflow.

### Step 1: Inspect

Inspect the relevant project files.

Determine:

- Minecraft version
- loader
- loader version
- Java version
- mappings
- existing architecture

### Step 2: Search

Search the repository for similar code.

Reuse established project patterns where appropriate.

### Step 3: Verify

Verify uncertain APIs against the actual current dependencies or source.

### Step 4: Implement

Make the smallest coherent change needed to complete the requested feature.

### Step 5: Compile

Run the appropriate Gradle compilation or build task.

Typical commands may include:

```bash
./gradlew compileJava
```

or:

```bash
./gradlew build
```

On Windows, use the appropriate wrapper command if necessary:

```bat
gradlew.bat build
```

Use the command appropriate for the current project.

### Step 6: Diagnose

If compilation fails, read the actual compiler error.

Do not immediately guess.

Determine whether the problem is caused by:

- wrong API version
- wrong mapping
- wrong import
- changed method signature
- missing dependency
- loader mismatch
- Java version mismatch
- incorrect generic type
- project logic error

### Step 7: Verify Again

Inspect the relevant source/API and correct the implementation.

### Step 8: Recompile

Compile again.

Continue until the implementation compiles successfully or there is a genuine external blocker.

---

## 11. Compilation Is Required

For code changes, compilation is part of implementation.

Do not claim that a coding task is complete without verification when compilation is available.

At minimum, run the most relevant compile task.

For larger changes, prefer:

```bash
./gradlew build
```

If the build cannot be run, clearly explain why.

Never hide compilation errors.

---

## 12. Never Fix Errors by Random Guessing

When compilation produces an error such as:

```text
cannot find symbol
```

do not simply substitute another remembered method.

Instead:

1. Identify the missing symbol.
2. Determine which dependency should provide it.
3. Inspect that dependency.
4. Find the actual API available in this version.
5. Update the implementation.
6. Compile again.

Compiler errors are evidence.

Use them to determine what the current API actually supports.

---

## 13. Do Not Change Versions to Make Code Work

Do not modify:

- Minecraft version
- loader version
- mappings
- Java version
- Gradle version
- dependencies

simply because generated code does not compile.

The code must adapt to the project's intended environment.

Only change versions or dependencies when the user explicitly requests it or when the task genuinely requires a dependency change.

If such a change is necessary, explain why before treating it as part of the solution.

---

## 14. Do Not Silently Add Dependencies

Avoid adding libraries merely to solve something that can reasonably be implemented with existing dependencies.

Before adding a dependency:

1. Check whether the project already provides the functionality.
2. Check whether Minecraft or the loader provides it.
3. Determine whether the dependency is compatible with the current environment.

Do not silently add unnecessary dependencies.

---

## 15. Preserve Project Architecture

Do not perform unrelated refactors while implementing a requested feature.

Avoid:

- renaming unrelated classes
- moving unrelated packages
- reformatting the entire repository
- changing build configuration unnecessarily
- replacing working systems without reason
- deleting code merely because it appears unused without verifying usage

Prefer focused changes.

---

## 16. Registration Systems

Registries frequently change between Minecraft and loader versions.

Before implementing registration for:

- blocks
- items
- entities
- sounds
- particles
- menus
- creative tabs
- data components
- enchantments
- recipes
- serializers
- custom registries

inspect how registration is performed in this exact project version.

Do not assume an older `DeferredRegister`, registry event, bootstrap, or direct registry pattern still applies.

---

## 17. Events

Event systems are loader-specific and version-sensitive.

Before subscribing to an event, verify:

- the event exists
- the package is correct
- which event bus it uses
- whether registration is automatic
- whether static subscribers are supported
- whether the event runs on client, server, or both
- whether the event has changed names between versions

Do not copy event handling code from another loader without verification.

---

## 18. Client and Server Separation

Minecraft has strict client/server separation.

Never reference client-only classes from common/server code unless the architecture explicitly supports it.

Be especially careful with:

- Minecraft client classes
- screens
- renderers
- models
- key bindings
- client events
- client networking handlers

Dedicated server compatibility must be preserved.

A mod working in the development client does not automatically mean it works on a dedicated server.

---

## 19. Networking

Networking APIs change frequently.

Before creating packets or payloads, inspect the exact networking system used by the current loader/version.

Verify:

- packet/payload registration
- serialization
- codec APIs
- direction
- client/server handling
- thread requirements
- player access
- protocol registration

Never assume networking code from an older Minecraft version still applies.

---

## 20. Rendering

Rendering APIs are especially version-sensitive.

Before implementing:

- entity renderers
- block entity renderers
- custom models
- HUD elements
- GUI rendering
- particles
- shaders
- render types
- item rendering

inspect the current version's rendering API.

Do not blindly copy old rendering tutorials.

---

## 21. Data and Serialization

Minecraft's data systems change frequently.

Verify current APIs before using:

- NBT
- codecs
- stream codecs
- data components
- saved data
- attachments
- capabilities
- persistent entity data
- custom serialization

Prefer the system intended for the current Minecraft/loader version.

Do not force an obsolete system into a newer version merely because it is familiar.

---

## 22. World Generation

World generation is highly version-sensitive.

Before modifying:

- biomes
- ores
- structures
- features
- dimensions
- configured features
- placed features
- biome modifiers
- datapack registries

inspect the current project's version and existing patterns.

Prefer data-driven systems when the current Minecraft version expects them.

---

## 23. Resources

When adding assets or data, verify paths carefully.

Common locations include:

```text
src/main/resources/assets/<modid>/
src/main/resources/data/<modid>/
```

Check:

- namespace
- filenames
- JSON structure
- texture paths
- model paths
- translation keys
- loot tables
- recipes
- tags

Do not assume resource formats are identical across Minecraft versions.

---

## 24. Mod ID Consistency

Before creating resources or registration identifiers, determine the project's actual mod ID.

Use the same mod ID consistently across:

- Java code
- registry names
- resource paths
- translation keys
- metadata
- networking
- data files

Never invent a new mod ID when one already exists.

---

## 25. Java Version

Use the Java version configured by the project.

Do not introduce Java features unavailable in the configured version.

Likewise, do not unnecessarily rewrite modern code into older Java syntax when the project supports modern syntax.

---

## 26. Error Handling

Do not suppress exceptions simply to make code appear functional.

Avoid broad patterns such as:

```java
try {
    // code
} catch (Exception ignored) {
}
```

unless there is a specific justified reason.

Prefer fixing the underlying problem.

Logging should provide useful diagnostic information when failures are recoverable.

---

## 27. No Fake Compatibility

Never claim that code supports multiple Minecraft versions unless that compatibility has actually been implemented and verified.

Do not describe code as:

- version-independent
- universal
- compatible with all modern versions
- Forge/NeoForge compatible

without evidence.

---

## 28. External Examples

Online tutorials and repositories may target different versions.

Before copying an external example, compare:

- Minecraft version
- loader
- loader version
- mappings
- Java version
- API version

Use external code as a reference, not as proof that an API exists here.

---

## 29. Documentation Rules

When documentation is available locally, use it.

If this repository contains directories such as:

```text
docs/
references/
examples/
api/
```

inspect relevant documentation before implementing unfamiliar systems.

However, actual project dependencies remain the final authority for API availability.

---

## 30. Code Quality

Generated code should be:

- readable
- maintainable
- appropriately modular
- consistent with surrounding code
- free of unnecessary duplication
- free of dead experimental code
- reasonably documented when behavior is non-obvious

Avoid overengineering simple features.

Do not create unnecessary abstraction layers merely to make the implementation appear sophisticated.

---

## 31. Comments

Comments should explain **WHY** something exists, especially for:

- version-specific workarounds
- unusual loader behavior
- client/server restrictions
- compatibility requirements
- non-obvious algorithms

Avoid comments that merely restate the code.

Bad:

```java
// Set health to 20
entity.setHealth(20);
```

Useful:

```java
// Apply this after entity initialization because attributes are not finalized
// during the earlier registration phase in this loader version.
```

---

## 32. Temporary Code

Do not leave:

- debug prints
- temporary test commands
- commented-out experiments
- unused imports
- placeholder implementations
- fake TODO implementations

unless they are intentionally required.

Remove temporary debugging code after verification.

---

## 33. Testing

When tests exist, run relevant tests after changes.

For Minecraft-specific functionality that cannot be fully unit-tested, at minimum verify:

- compilation
- resource validity when possible
- registration correctness
- obvious client/server separation issues

For significant changes, use the project's available run or test configurations when practical.

---

## 34. Gradle

Use the project's Gradle Wrapper.

Prefer:

```bash
./gradlew <task>
```

instead of relying on a globally installed Gradle version.

Do not modify Gradle configuration unnecessarily.

If dependency resolution fails, determine whether the issue is:

- network-related
- repository-related
- version-related
- configuration-related

before changing dependency versions.

---

## 35. When Compilation Fails

Treat build failures systematically.

Read the first meaningful error because later errors may be consequences of the first one.

Recommended process:

```text
Compile
↓
Read first meaningful error
↓
Locate affected class/API
↓
Inspect current dependency/source
↓
Determine actual signature
↓
Fix
↓
Compile again
```

Do not make large speculative changes based on dozens of downstream compiler errors.

---

## 36. When Runtime Fails

A successful compilation does not guarantee correct runtime behavior.

For runtime crashes:

1. Read the crash report or stack trace.
2. Identify the first relevant project class.
3. Determine the root exception.
4. Check loader/Minecraft lifecycle requirements.
5. Fix the underlying issue.
6. Re-run.

Do not diagnose runtime problems solely from the final line of a stack trace.

---

## 37. Version Migration

When upgrading the project from one Minecraft version to another:

Do not assume the migration consists only of changing version numbers.

Systematically inspect:

- Gradle configuration
- loader version
- mappings
- Java version
- registrations
- events
- networking
- rendering
- data systems
- resources
- world generation
- third-party dependencies

Resolve migration problems using the target version's actual APIs.

Do not preserve obsolete APIs merely to minimize code changes.

---

## 38. Feature Requests

When asked to add a feature:

1. Understand how it should behave.
2. Locate relevant existing systems.
3. Determine which APIs are required.
4. Verify those APIs for this project version.
5. Implement the feature.
6. Compile.
7. Fix compilation issues.
8. Verify integration with existing systems.

Do not begin by generating large amounts of code before understanding the project's existing implementation.

---

## 39. Bug Fixes

When asked to fix a bug:

Do not rewrite the entire system immediately.

First:

1. Reproduce or understand the reported behavior.
2. Trace the relevant code path.
3. Identify the root cause.
4. Make the smallest reliable fix.
5. Compile/test.
6. Check for nearby regressions.

Preserve working behavior unrelated to the bug.

---

## 40. Do Not Assume the User's Diagnosis Is Correct

A reported problem may have a different root cause.

For example:

```text
"The entity registration is broken."
```

may actually be caused by:

- renderer registration
- invalid resource location
- networking
- missing attributes
- initialization order
- incorrect side handling

Investigate evidence before modifying code.

---

## 41. Repository Search

Use repository search aggressively.

Before creating a new utility, registry, handler, or abstraction, check whether one already exists.

Search by:

- class name
- method name
- registry identifier
- resource location
- event type
- feature name

Avoid duplicate systems.

---

## 42. API Uncertainty Protocol

If you are not confident that an API is correct:

**STOP generating speculative implementation code.**

Instead:

1. Identify exactly what API information is missing.
2. Search the current project.
3. Search the current dependencies/source.
4. Confirm the correct API.
5. Continue implementation.

Never conceal uncertainty by generating plausible-looking Java.

Correctness is more important than producing code quickly.

---

## 43. Internet and Documentation Research

When internet access is available and the local project does not provide enough information:

1. Determine the exact Minecraft version.
2. Determine the exact loader and loader version.
3. Search official documentation for that exact environment.
4. Prefer primary sources.
5. Verify examples against the project's actual dependencies.

Preferred sources include:

- official NeoForge documentation
- official Fabric documentation
- official Forge documentation
- official loader GitHub repositories
- official Minecraft mappings/source where legally and technically available
- dependency source code

Do not trust a tutorial merely because its code looks plausible.

Always check its version.

---

## 44. Never Downgrade Correctness to Finish Faster

If an API cannot be verified, do not fabricate an implementation just to complete the task.

It is better to identify the missing information and investigate it than to produce code that merely looks correct.

Priority order:

```text
Correctness
> Version compatibility
> Runtime safety
> Maintainability
> Speed of implementation
```

---

## 45. Completion Criteria

A coding task is complete only when all relevant conditions are satisfied:

- requested behavior is implemented
- code matches the current Minecraft version
- code matches the current mod loader
- mappings are correct
- imports resolve
- code compiles
- relevant tests pass when available
- resources are correctly located
- no obvious client/server violation exists
- no unrelated functionality was unnecessarily changed

If any condition cannot be verified, explicitly state what remains unverified.

---

## 46. Final Response

After completing a coding task, provide a concise summary containing:

- what was changed
- important files changed
- verification performed
- build/test result
- any remaining limitations

Do not claim success when the build is failing.

If the build fails because of an external issue, distinguish that from a source-code compilation failure.

---

## 47. Core Rule

The fundamental rule for this repository is:

> Never write Minecraft modding code based solely on remembered APIs.

Always prefer:

```text
Inspect project
→ determine exact version
→ determine loader and mappings
→ inspect existing code
→ verify current API
→ implement
→ compile
→ diagnose
→ fix
→ compile again
```

over:

```text
Remember an API
→ generate plausible code
→ hope it works
```

The current project's source, resolved dependencies, documentation, and compiler are more trustworthy than memory.

---

## 48. Mandatory Pre-Coding Checklist

Before writing version-sensitive Minecraft code, confirm:

- [ ] Minecraft version identified
- [ ] Mod loader identified
- [ ] Loader version identified
- [ ] Java version identified
- [ ] Mapping environment identified
- [ ] Relevant existing project code inspected
- [ ] Relevant APIs verified
- [ ] No APIs copied blindly from another version
- [ ] No APIs mixed from another mod loader

After implementation:

- [ ] Imports resolve
- [ ] Code compiles
- [ ] Relevant Gradle task passes
- [ ] Resources are in correct locations
- [ ] Client/server separation checked
- [ ] No unnecessary dependency/version changes made
- [ ] No temporary debugging code remains

---

# FINAL MANDATORY INSTRUCTION

For Minecraft mod development, **never guess an API when it can be verified**.

If there is uncertainty:

**SEARCH FIRST. CODE SECOND. COMPILE THIRD. FIX FROM EVIDENCE.**

Do not rely on memory alone.

Do not assume APIs from another Minecraft version are compatible.

Do not assume Forge, NeoForge, Fabric, or Quilt APIs are interchangeable.

Do not declare the task complete until the implementation has been verified as far as the current environment allows.
