// port-lint: ignore
package io.github.kotlinmania.treesitterbash

// Per-target embedded-resource reader backing [NODE_TYPES] and [HIGHLIGHT_QUERY].
internal expect fun readBundledTreeSitterBashResource(name: String): String
