// port-lint: source lib.rs
package io.github.kotlinmania.treesitterbash

import io.github.kotlinmania.treesitterlanguage.LanguageFn

/**
 * This library provides Bash language support for the [Tree-sitter](https://tree-sitter.github.io/) parsing library.
 *
 * Typically, you will use the [LANGUAGE] constant to add this language to a
 * Tree-sitter parser, and then use the parser to parse some code:
 *
 * ```kotlin
 * val code = """
 * echo "hello world!"
 * """
 * val parser = Parser()
 * val language = LANGUAGE
 * parser.setLanguage(language.into())
 * val tree = parser.parse(code, null)
 * check(!tree.rootNode().hasError())
 * ```
 *
 * Additional references:
 * - [Parser documentation](https://docs.rs/tree-sitter)
 * - [Tree-sitter project](https://tree-sitter.github.io/)
 */

/**
 * Foreign function interface entry point resolving the C language pointer for Bash grammar.
 *
 * @return the raw native grammar pointer handle as a [Long]
 */
public fun treeSitterBash(): Long {
    val ptr = nativeLanguagePointer()
    return ptr
}

/**
 * The Tree-sitter [LanguageFn] for this grammar.
 *
 * @see <a href="https://docs.rs/tree-sitter-language">LanguageFn documentation</a>
 */
public val LANGUAGE: LanguageFn = LanguageFn.fromRaw(::treeSitterBash)

/**
 * The content of the [`node-types.json`](https://tree-sitter.github.io/tree-sitter/using-parsers#static-node-types)
 * file for this grammar.
 */
public val NODE_TYPES: String by lazy { readBundledTreeSitterBashResource("node-types.json") }

/**
 * The syntax highlighting query for this grammar.
 */
public val HIGHLIGHT_QUERY: String by lazy { readBundledTreeSitterBashResource("queries/highlights.scm") }
