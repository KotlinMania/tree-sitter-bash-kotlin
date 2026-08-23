// port-lint: source bindings/rust/build.rs
package io.github.kotlinmania.treesitterbash

/**
 * Tracking ledger for the upstream Cargo build script. The Rust crate's
 * build script invokes the C compiler to compile the bundled parser and
 * scanner into a static library that the foreign function declaration
 * links against.
 *
 * Kotlin Multiplatform has no per-source build script of its own — the
 * equivalent is the workspace Gradle build, which owns native compilation
 * per Kotlin target:
 *
 *   - Kotlin/Native (Linux, macOS, iOS, tvOS, watchOS, Windows MinGW,
 *     Android Native): the cinterop block on each native target compiles
 *     the bundled parser and scanner C files against the header and exposes
 *     the entry point that [treeSitterBash] resolves through.
 *   - JVM / Android: a host-side JNI shared library compiled from the same
 *     C files and loaded via System.loadLibrary by the per-target
 *     [TreeSitterBash] actual.
 *   - Kotlin/JS + Wasm-JS + Wasm-WASI: the upstream Web bindings provide
 *     a WebAssembly artifact that the per-target actual reads from
 *     resources.
 *
 * This file exists so the upstream build script is tracked
 * to a commonMain Kotlin file with the `port-lint: source build.rs` header;
 * its body is documentation only because the translation target is the
 * Gradle build, not Kotlin source.
 */
internal object Build
