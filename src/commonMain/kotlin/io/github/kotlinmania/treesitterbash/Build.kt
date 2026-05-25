// port-lint: source build.rs
package io.github.kotlinmania.treesitterbash

/**
 * Tracking ledger for the upstream Cargo build script. The Rust crate's
 * `build.rs` does one thing: it invokes the `cc` crate to compile the
 * bundled `src/parser.c` and `src/scanner.c` (with `-std=c11` and the
 * MSVC `-utf-8` flag where applicable) into a static library named
 * `tree-sitter-bash` that the Rust `extern "C" fn tree_sitter_bash()`
 * declaration in `lib.rs` links against.
 *
 * Kotlin Multiplatform has no per-source build script of its own — the
 * equivalent of `build.rs` is the workspace `build.gradle.kts`, which
 * owns native compilation per Kotlin target:
 *
 *   - Kotlin/Native (Linux, macOS, iOS, tvOS, watchOS, Windows MinGW,
 *     Android Native): the `cinterop` block on each native target
 *     compiles `tmp/tree-sitter-bash/src/parser.c` +
 *     `tmp/tree-sitter-bash/src/scanner.c` against the bundled
 *     `tree_sitter/parser.h` and exposes `tree_sitter_bash()` as the
 *     C entry-point that [treeSitterBash] resolves through.
 *   - JVM / Android: a host-side JNI shared library (`libtree-sitter-bash.so`,
 *     `.dylib`, `.dll`) compiled from the same two C files and loaded
 *     via `System.loadLibrary("tree-sitter-bash")` by the per-target
 *     [TreeSitterBash] actual.
 *   - Kotlin/JS + Wasm-JS + Wasm-WASI: the upstream Web bindings provide
 *     a `tree-sitter-bash.wasm` artifact that the per-target actual
 *     reads from `BUNDLED_TREE_SITTER_BASH_RESOURCES`.
 *
 * This file exists so `ast_distance` can match upstream `build.rs` to a
 * commonMain Kotlin file with the `port-lint: source build.rs` header;
 * its body is documentation only because the translation target is the
 * Gradle build, not Kotlin source.
 *
 * @see <a href="https://docs.rs/cc/latest/cc/">The Rust `cc` crate that upstream `build.rs` uses</a>
 */
internal object Build
