// port-lint: source lib.rs
package io.github.kotlinmania.treesitterbash

/**
 * Kotlin/Wasm-JS actual for [treeSitterBash]. Same constraint as the
 * Kotlin/JS sibling: `web-tree-sitter` requires an async `Parser.init()`
 * that does not fit the synchronous `(): Long` signature exposed by the
 * commonMain `expect`. Until the `AsyncLanguageProvider` follow-up lands,
 * each wasm-js caller stops here with a clear runtime error rather than
 * returning a null pointer.
 */
internal actual fun treeSitterBash(): Long =
    throw UnsupportedOperationException(
        "tree-sitter-bash grammar binding on Kotlin/Wasm-JS requires an " +
            "async web-tree-sitter load that does not yet bridge to the " +
            "synchronous LanguageFn API.",
    )
