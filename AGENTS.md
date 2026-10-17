# AGENTS.md

## 1. Project purpose

This repository contains a Minecraft mod generated from the Architectury Template Generator at https://generate.architectury.dev/.

The project is a multiplatform Architectury project. It may contain a shared `common` module and one or more loader-specific modules such as `fabric`, `forge`, `neoforge`, or `quilt`.

The highest priority is version-correct, loader-correct, and cross-platform implementation.

Never assume that an API remembered from another Minecraft version, mapping set, loader, or Architectury template is valid here.

## 2. Source of truth

Before changing code, inspect the actual repository configuration. Treat these files as authoritative:

- `gradle.properties`
- `build.gradle` or `build.gradle.kts`
- `settings.gradle` or `settings.gradle.kts`
- `common/`
- each enabled platform module
- `fabric.mod.json`
- `META-INF/mods.toml`
- `META-INF/neoforge.mods.toml`
- `quilt.mod.json`
- `libs.versions.toml`, when present
- existing source files and imports
- Gradle dependency declarations and resolved dependencies

Determine before implementation:

- Minecraft version
- enabled platforms from `enabled_platforms`
- platform modules included by `settings.gradle`
- mappings and mapping patch versions
- Java and Gradle versions
- Architectury Loom, Architectury Plugin, and Architectury API versions
- loader versions and platform API versions
- mod ID, package name, Maven group, and artifact names

Do not invent or silently update these values. If the task requires a version change, make it explicit and update all affected configuration consistently.

## 3. Architectury project layout

Follow the source-set boundaries established by the generated project:

- `common`: shared gameplay logic, registries, data, resources, interfaces, and code that can run on every enabled platform
- `fabric`: Fabric Loader and Fabric API integration only
- `forge`: Forge integration only
- `neoforge`: NeoForge integration only
- `quilt`: Quilt integration only

Use common code by default. Put code in a platform module only when the required behavior or API genuinely differs between loaders.

Do not import Fabric, Forge, NeoForge, Quilt, or loader-specific libraries from `common` unless the generated project explicitly provides a verified cross-platform abstraction for that API.

Keep platform-specific code behind a small common interface, service, or Architectury-provided abstraction. Avoid duplicating shared gameplay logic in every platform module.

## 4. Platform and loader safety

Identify the active platform before editing platform code. Use only the APIs and dependencies declared for that platform.

- Fabric code may use Fabric Loader and the declared Fabric API version.
- Forge code may use the declared Forge version and Forge APIs.
- NeoForge code may use the declared NeoForge version and NeoForge APIs.
- Quilt code may use Quilt Loader and the declared Quilt APIs.

Do not mix Forge and NeoForge APIs. Do not use Fabric or Quilt APIs in Forge or NeoForge code. Do not assume that similarly named events, registries, annotations, networking systems, or lifecycle hooks are interchangeable.

If a feature has different implementations, keep the common contract stable and implement the loader-specific details in the appropriate module.

## 5. Minecraft and mapping safety

Minecraft APIs change substantially between versions. Before using a version-sensitive class, method, constructor, field, event, registry, annotation, rendering API, networking API, data component, command API, or resource API, verify it against this project.

Check, in this order:

1. Existing code in this repository
2. The project's resolved dependencies and attached sources
3. Generated Minecraft sources for the exact version and mappings
4. Architectury and loader documentation for the exact versions
5. Trusted examples targeting the same version, mappings, and loader

Mapping names are not universal. Confirm whether the project uses Yarn, Mojang mappings, intermediary mappings, or a generated mapping layer before copying names from an example.

Never solve an uncertain API by guessing several method or class names until one compiles.

## 6. API and dependency verification

Before calling an unfamiliar API, verify all of the following:

- the class exists
- the package is correct
- the API belongs to the current loader or to common code
- the method or constructor exists in this exact version
- parameters and generic types match
- the return type matches
- the dependency is available to the source set being edited
- the API is safe on every enabled platform, if used in `common`

Do not add a platform dependency to `common` merely to make compilation pass. First look for an existing Architectury abstraction or move the implementation to the correct platform module.

## 7. Existing code first

Before implementing a feature, search the repository for the established pattern. Check existing implementations of:

- item, block, entity, menu, screen, and registry setup
- creative tabs and tags
- commands and events
- networking and packet registration
- configuration, saved data, and serialization
- world generation and data generation
- rendering and client-only initialization
- platform services and loader entrypoints
- resources and generated data

Preserve the project's naming, registration order, package structure, initialization lifecycle, and logging style unless there is a clear reason to change them.

## 8. Registration and initialization

Register shared content through the common registration path used by the generated project. Keep loader entrypoints thin and deterministic.

Separate common initialization from client initialization. Do not load client-only classes on a dedicated server. Do not reference rendering classes, client-only events, or client-only packages from common server paths.

When adding a registration:

- use the project's existing registry abstraction when one exists
- use the correct `ResourceLocation` or equivalent identifier format for this Minecraft version
- keep the mod ID consistent with the generated metadata
- register platform-specific behavior in the platform module
- add all required translations, models, blockstates, tags, recipes, loot tables, and metadata

## 9. Networking, data, and resources

Verify the networking API for every enabled loader and Minecraft version. Keep packet payloads and shared validation logic in `common` where possible, while keeping transport registration platform-specific when required.

Treat client input as untrusted. Validate permissions, distances, state, and payload contents on the server.

When adding data or resources, check every enabled platform for its required metadata and resource conventions. Do not assume that a Fabric resource or metadata file is sufficient for Forge, NeoForge, or Quilt.

## 10. Gradle and generated-project rules

Preserve the generated multi-project structure unless the user explicitly asks to change it.

When adding a platform:

- update `enabled_platforms` in `gradle.properties`
- include the matching subproject in `settings.gradle` or `settings.gradle.kts`
- add the platform's loader and API versions
- add the platform entrypoint and metadata
- confirm that common code compiles against the platform
- run the platform-specific build and relevant run configuration

Use the generated Gradle wrapper rather than a globally installed Gradle version.

Prefer targeted tasks while iterating, then verify the complete build. Typical task names may include:

- `build`
- `:common:build`
- `:fabric:build`
- `:forge:build`
- `:neoforge:build`
- `:quilt:build`

Only run tasks for modules that actually exist in this repository. Do not assume every Architectury project enables every platform.

Do not upgrade Loom, Architectury, mappings, loaders, or Minecraft as part of an unrelated feature. Keep dependency changes minimal and explain any necessary compatibility change.

## 11. Testing requirements

After changing common code, test every enabled platform that can consume it.

At minimum:

- compile or build the common module
- build each enabled platform module
- run the relevant client or server smoke test when the change affects initialization, registries, networking, resources, rendering, or commands
- inspect logs for loader-specific warnings and missing resources

If a platform cannot be tested locally, state which platform was not verified and why. Do not claim cross-platform compatibility from a single-loader build.

## 12. Search and implementation discipline

Search before guessing. Use repository search and dependency inspection to locate the real implementation.

Do not invent:

- package names
- mappings
- loader events
- registry names
- entrypoint names
- annotations
- Gradle task names
- metadata formats
- platform abstractions

Keep changes focused. Avoid unrelated formatting, dependency upgrades, generated-file churn, or loader migration.

## 13. Completion checklist

Before declaring a task complete, confirm:

- the implementation matches the exact Minecraft version and mappings
- common code has no accidental loader-specific dependency
- every enabled platform has the required platform implementation or correctly uses the common implementation
- common and platform registration paths are complete
- client-only code is isolated from server initialization
- resources and metadata exist for every affected platform
- relevant Gradle builds or tests pass
- any unverified platform or known limitation is clearly reported
