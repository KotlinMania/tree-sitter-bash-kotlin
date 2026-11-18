package io.github.kotlinmania.treesitterbash

/**
 * Kotlin/JS actual for [treeSitterBash]. The upstream JS equivalent is
 * [`web-tree-sitter`](https://www.npmjs.com/package/web-tree-sitter) +
 * the [`tree-sitter-bash`](https://www.npmjs.com/package/tree-sitter-bash)
 * grammar WASM. Loading them requires an asynchronous `Parser.init()` call
 * that does not fit the synchronous `(): Long` signature exposed by the
 * commonMain `expect`, so the bridge needs an `expect class
 * AsyncLanguageProvider` follow-up. Until that lands, every Kotlin/JS
 * caller stops here with a clear runtime error rather than handing back a
 * zero pointer that downstream `Language(0L)` callers would dereference.
 */
internal actual fun nativeLanguagePointer(): Long =
    throw UnsupportedOperationException(
        "tree-sitter-bash grammar binding on Kotlin/JS requires an async " +
            "web-tree-sitter load (npm 'web-tree-sitter' + 'tree-sitter-bash') " +
            "that does not yet bridge to the synchronous LanguageFn API. " +
            "Use the JVM / Android target until the AsyncLanguageProvider " +
            "follow-up lands.",
    )
