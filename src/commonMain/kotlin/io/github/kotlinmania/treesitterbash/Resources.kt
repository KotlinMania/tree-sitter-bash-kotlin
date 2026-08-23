package io.github.kotlinmania.treesitterbash

/**
 * Per-target embedded-resource reader backing [NODE_TYPES] and [HIGHLIGHT_QUERY].
 */
internal fun readBundledTreeSitterBashResource(name: String): String {
    val content = BUNDLED_TREE_SITTER_BASH_RESOURCES[name]
    if (content != null) {
        return content
    }
    return ""
}
