package io.github.kotlinmania.treesitterbash

/**
 * Kotlin/Wasm-WASI actual for [treeSitterBash]. WASI has neither
 * `web-tree-sitter`'s JS environment nor a Kotlin/Native cinterop, so
 * loading the grammar would require a custom Wasm component embedding
 * the compiled `parser.c`/`scanner.c`. Until that lands, every wasm-wasi
 * caller stops here with a clear runtime error.
 */
internal actual fun nativeLanguagePointer(): Long =
    throw UnsupportedOperationException(
        "tree-sitter-bash grammar binding is not available on Kotlin/Wasm-WASI. " +
            "WASI does not provide JS interop, and no Wasm-WASI port of " +
            "libtree-sitter-bash is bundled yet.",
    )
