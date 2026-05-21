// port-lint: source bindings/rust/lib.rs
package io.github.kotlinmania.treesitterbash

/**
 * The syntax highlighting query for this grammar.
 */
val HIGHLIGHT_QUERY: String by lazy { readBundledTreeSitterBashResource("queries/highlights.scm") }
