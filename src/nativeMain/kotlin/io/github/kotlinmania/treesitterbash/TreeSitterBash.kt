package io.github.kotlinmania.treesitterbash

/**
 * Kotlin/Native actual for [treeSitterBash]. The upstream Rust crate exposes
 * the grammar via `extern "C" fn tree_sitter_bash() -> *const TSLanguage`,
 * compiled from the bundled `parser.c`/`scanner.c`. On Kotlin/Native this
 * requires a per-target cinterop binding to a compiled
 * `libtree-sitter-bash` static library; until that build pipeline is wired,
 * each native target stops here with a clear runtime error rather than
 * returning a null pointer that downstream `Language(0L)` callers would
 * dereference. Same shape the upstream `tree-sitter-rust` crate uses for
 * targets where the grammar is not bundled.
 */
internal actual fun nativeLanguagePointer(): Long =
    throw UnsupportedOperationException(
        "tree-sitter-bash grammar binding is not wired on Kotlin/Native yet. " +
            "Add a per-target cinterop to libtree-sitter-bash (.a) before " +
            "calling LANGUAGE.intoRaw().call().",
    )
