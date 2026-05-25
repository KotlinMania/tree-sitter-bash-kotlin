package io.github.kotlinmania.treesitterbash

/**
 * Per-target embedded-resource reader backing [NODE_TYPES] and [HIGHLIGHT_QUERY].
 *
 * Resolves against the [BUNDLED_TREE_SITTER_BASH_RESOURCES] map generated at
 * build time by `generateBundledTreeSitterBashResources` from
 * `src/commonMain/resources/io/github/kotlinmania/treesitterbash/`. Lives in
 * commonMain (not expect/actual) so every Kotlin target — Native, JS,
 * Wasm-JS, Wasm-WASI included — reads the strings the same way without
 * relying on the JVM classpath or platform-specific filesystem APIs.
 */
internal fun readBundledTreeSitterBashResource(name: String): String =
    BUNDLED_TREE_SITTER_BASH_RESOURCES[name]
        ?: error("Bundled tree-sitter-bash resource not generated: $name")
