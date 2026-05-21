# Next steps to green CI on main

This file inventories the remaining work to take `./gradlew build` from its current state to a fully green all-target build on `main`. The slot that produced PRs #16, #17, #19, #20, #21, #22, and #23 left the dep tree partly resolved; this file is the handoff.

## Current state of main

- `compileCommonMainKotlinMetadata` — GREEN. `Language.kt`'s `io.github.kotlinmania.treesitterlanguage.LanguageFn` import resolves against the new [tree-sitter-language-kotlin](https://github.com/KotlinMania/tree-sitter-language-kotlin) sibling at `0.1.0` via `mavenLocal()`.
- `compileKotlinJvm` — GREEN. JNI binding + classpath resource read in `src/jvmMain/`.
- `compileAndroidMain` — GREEN. Identical pattern in `src/androidMain/`.
- All other targets — RED. See below.
- `commonTest` — RED. `LibTest.kt` imports `io.github.kotlinmania.treesitter.Parser`, blocked on the tree-sitter-kotlin runtime sibling.

## Remaining blockers

### 1. Native cinterop for `treeSitterBash()` and resource reads

Affects: `macosArm64`, `iosArm64`, `iosSimulatorArm64`, `iosX64`, `tvosArm64`, `tvosSimulatorArm64`, `watchosArm32`, `watchosArm64`, `watchosDeviceArm64`, `watchosSimulatorArm64`, `linuxX64`, `linuxArm64`, `mingwX64`, `androidNativeArm32`, `androidNativeArm64`, `androidNativeX86`, `androidNativeX64`.

Required pieces:
- A `.def` file at `src/nativeInterop/cinterop/treesitterbash.def` declaring the `tree_sitter_bash` extern and naming the static library.
- A small header `src/nativeInterop/headers/tree-sitter-bash.h` with `const TSLanguage *tree_sitter_bash(void);` plus a forward decl for `TSLanguage`.
- A Gradle task per native target that compiles `tmp/tree-sitter-bash/src/parser.c` and `tmp/tree-sitter-bash/src/scanner.c` to `build/libs/<target>/libtree-sitter-bash.a` using `run_konan clang`. Pattern: `codex-kotlin/ktreesitter-kotlin/languages/bash/build.gradle.kts`.
- `cinterops { create("treesitterbash") { ... } }` block in every native target's compilation.
- `src/nativeMain/kotlin/io/github/kotlinmania/treesitterbash/TreeSitterBash.kt` actual that calls the cinterop'd `tree_sitter_bash()` and converts the pointer to `Long`.
- `src/nativeMain/kotlin/io/github/kotlinmania/treesitterbash/Resources.kt` actual that returns the embedded resource. For native targets without a runtime classpath, the simplest path is to embed both resource bodies as Kotlin string constants in `nativeMain`-level generated source (similar to Rust's `include_str!`).

### 2. JS / WasmJS / WasmWasi actuals

These targets cannot run native C code directly. Options:
- Skip these targets in the build by removing them from `kotlin { ... }`. Workspace runbook discourages target shrinking — only acceptable if Sydney explicitly opts out.
- Compile `parser.c`+`scanner.c` to WASM with Emscripten, load via `WebAssembly.instantiate` in `jsMain`/`wasmJsMain`/`wasmWasiMain`. Substantial separate piece of work.

### 3. Package the JNI native library for JVM/Android runtime

JVM `compileKotlinJvm` is green at compile time, but the JNI binding `nativeTreeSitterBashLanguagePointer` is unresolved at runtime — no `libtree-sitter-bash.{so,dylib,dll}` is packaged. To make the JVM JAR actually load:
- Add JNI wrapper C source (one `.c` file) that exports `Java_io_github_kotlinmania_treesitterbash_TreeSitterBashKt_nativeTreeSitterBashLanguagePointer` and calls `tree_sitter_bash()`.
- Compile parser.c+scanner.c+the JNI wrapper into a shared library per JVM-supported OS/arch (linux-x64, linux-arm64, macos-arm64, macos-x64, windows-x64, android-arm64, android-arm32, android-x64, android-x86).
- Stage them at `src/jvmMain/resources/native/<os-arch>/libtree-sitter-bash.{so,dylib,dll}` and use a small bootstrap helper that extracts the right `.so` to a temp dir before `System.loadLibrary`. Or use `jnr-ffi` / `jna` for an alternative loader.

### 4. tree-sitter-kotlin runtime sibling port

Blocks `LibTest.kt`'s `io.github.kotlinmania.treesitter.Parser` import. The repo exists at [KotlinMania/tree-sitter-kotlin](https://github.com/KotlinMania/tree-sitter-kotlin) with zero `.kt` files. Per its `PORT_REPORT.md`, this is rank 102 in the workspace priority queue and the upstream port target is the full `tree-sitter` Rust crate (`binding_rust/lib.rs` is ~3000 lines plus `bindings.rs`/`ffi.rs`/`util.rs`).

Bottom-up port order suggested:
1. Pure data types from `binding_rust/lib.rs`: `Point`, `Range`, `InputEdit`, `LanguageMetadata`. No native calls.
2. `Language` type — wraps `*const TSLanguage`. Cinterop touch but minimal.
3. `LanguageFn.into() -> Language` — extension that resolves the LanguageFn's C function pointer and constructs a `Language`. This goes in `tree-sitter-language-kotlin` and depends on the `Language` type from `tree-sitter-kotlin`, OR `tree-sitter-kotlin` adds the conversion as a member function on `Language`.
4. `Parser` with `setLanguage(Language?): Boolean` minimum.
5. `Tree`, `Node`, query and cursor types.
6. The actual C library bindings via cinterop (`tmp/tree-sitter/src/lib.c` and friends are the C runtime — would also need compilation).

Realistically multi-day work.

## Per-PR provenance for this slot

- [#16](https://github.com/KotlinMania/tree-sitter-bash-kotlin/pull/16) — Android SDK installer into Gradle.
- [#17](https://github.com/KotlinMania/tree-sitter-bash-kotlin/pull/17) — Initial lib.rs port (monolithic Lib.kt).
- [#18](https://github.com/KotlinMania/tree-sitter-bash-kotlin/pull/18) — `fullTargetBuildTaskNames` build gate.
- [#19](https://github.com/KotlinMania/tree-sitter-bash-kotlin/pull/19) — Parcel Lib.kt into focused files per .ast_distance_config.json opt-in.
- [#20](https://github.com/KotlinMania/tree-sitter-bash-kotlin/pull/20), [#21](https://github.com/KotlinMania/tree-sitter-bash-kotlin/pull/21) — Sibling-package import fix and revert.
- [#22](https://github.com/KotlinMania/tree-sitter-bash-kotlin/pull/22) — Wire tree-sitter-language-kotlin sibling dep + conditional signing.
- [#23](https://github.com/KotlinMania/tree-sitter-bash-kotlin/pull/23) — JVM and Android actuals.
- [KotlinMania/tree-sitter-language-kotlin](https://github.com/KotlinMania/tree-sitter-language-kotlin) — new sibling repo created this slot.
