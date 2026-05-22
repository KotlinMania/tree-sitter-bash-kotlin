// port-lint: source lib.rs
package io.github.kotlinmania.treesitterbash

/**
 * The content of the [`node-types.json`](https://tree-sitter.github.io/tree-sitter/using-parsers#static-node-types)
 * file for this grammar.
 */
val NODE_TYPES: String by lazy { readBundledTreeSitterBashResource("node-types.json") }
