// port-lint: source bindings/rust/lib.rs
package io.github.kotlinmania.treesitterbash

import io.github.kotlinmania.treesitterlanguage.LanguageFn

/**
 * This crate provides Bash language support for the [tree-sitter] parsing library.
 *
 * Typically, you will use the [LANGUAGE] constant to add this language to a
 * tree-sitter [Parser], and then use the parser to parse some code:
 *
 * ```kotlin
 * val parser = Parser()
 * parser.setLanguage(LANGUAGE.into())
 * val tree = parser.parse(code, null)
 * ```
 *
 * @see <a href="https://tree-sitter.github.io/">tree-sitter</a>
 */

/**
 * Raw foreign function interface entry point for the Tree-sitter Bash C grammar pointer.
 */
fun treeSitterBash(): Long {
    val ptr = treeSitterBashRaw()
    return ptr
}

/**
 * The tree-sitter [LanguageFn] for this grammar.
 *
 * @see <a href="https://docs.rs/tree-sitter-language">LanguageFn upstream documentation</a>
 */
val LANGUAGE: LanguageFn = LanguageFn.fromRaw(::treeSitterBash)

/**
 * Returns the [LanguageFn] for this grammar.
 */
fun language(): LanguageFn = LANGUAGE

/**
 * The content of the [`node-types.json`](https://tree-sitter.github.io/tree-sitter/using-parsers#static-node-types)
 * file for this grammar.
 */
val NODE_TYPES: String by lazy { readBundledTreeSitterBashResource("node-types.json") }

/**
 * Returns the `node-types.json` content for this grammar.
 */
fun nodeTypes(): String = NODE_TYPES

/**
 * The syntax highlighting query for this grammar.
 */
val HIGHLIGHT_QUERY: String by lazy { readBundledTreeSitterBashResource("queries/highlights.scm") }

/**
 * Returns the syntax highlighting query for this grammar.
 */
fun highlightQuery(): String = HIGHLIGHT_QUERY
