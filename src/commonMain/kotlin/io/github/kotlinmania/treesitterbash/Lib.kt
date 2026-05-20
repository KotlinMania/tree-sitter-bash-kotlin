// port-lint: source bindings/rust/lib.rs
package io.github.kotlinmania.treesitterbash

import io.github.kotlinmania.treesitterlanguage.LanguageFn

/**
 * This module provides Bash language support for the [tree-sitter](https://tree-sitter.github.io/) parsing library.
 *
 * Typically, you will use the [LANGUAGE] constant to add this language to a
 * tree-sitter `Parser`, and then use the parser to parse some code:
 *
 * ```kotlin
 * import io.github.kotlinmania.treesitter.Parser
 *
 * val code = """
 * echo "hello world!"
 * """
 * val parser = Parser()
 * val language = LANGUAGE
 * parser.setLanguage(language.into())
 *     ?: error("Error loading Bash parser")
 * val tree = parser.parse(code, null)!!
 * check(!tree.rootNode().hasError())
 * ```
 *
 * Documentation references:
 *  - `Parser`: kotlin-tree-sitter sibling port's `Parser`.
 *  - tree-sitter: https://tree-sitter.github.io/.
 */

// extern "C" { fn tree_sitter_bash() -> *const () }
internal expect fun treeSitterBash(): Long

/**
 * The tree-sitter [LanguageFn] for this grammar.
 */
val LANGUAGE: LanguageFn = LanguageFn.fromRaw(::treeSitterBash)

/**
 * The content of the `node-types.json` file for this grammar.
 *
 * See https://tree-sitter.github.io/tree-sitter/using-parsers#static-node-types.
 */
val NODE_TYPES: String by lazy { readBundledTreeSitterBashResource("node-types.json") }

/**
 * The syntax highlighting query for this grammar.
 */
val HIGHLIGHT_QUERY: String by lazy { readBundledTreeSitterBashResource("queries/highlights.scm") }

/**
 * Loads a bundled grammar resource by path relative to the
 * `io/github/kotlinmania/treesitterbash/` resources root. This is the
 * Kotlin Multiplatform stand-in for Rust's `include_str!`: each platform
 * supplies its own embedded-resource read so [NODE_TYPES] and
 * [HIGHLIGHT_QUERY] resolve to the bundled file contents.
 */
internal expect fun readBundledTreeSitterBashResource(name: String): String
