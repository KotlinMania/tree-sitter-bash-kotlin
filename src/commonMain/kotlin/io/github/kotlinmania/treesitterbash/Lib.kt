// port-lint: source bindings/rust/lib.rs
package io.github.kotlinmania.treesitterbash

import io.github.kotlinmania.treesitterlanguage.LanguageFn

/**
 * This crate provides Bash language support for the [tree-sitter][] parsing library.
 *
 * Typically, you will use the [LANGUAGE][] constant to add this language to a
 * tree-sitter [Parser][], and then use the parser to parse some code:
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
 * [Parser]: https://docs.rs/tree-sitter
 * [tree-sitter]: https://tree-sitter.github.io/
 */

/**
 * Foreign function interface entry point resolving the C language pointer for Bash grammar.
 *
 * @return the raw native grammar pointer handle as a [Long]
 */
public fun treeSitterBash(): Long {
    return nativeLanguagePointer()
}

/**
 * The tree-sitter [`LanguageFn`][LanguageFn] for this grammar.
 *
 * [LanguageFn]: https://docs.rs/tree-sitter-language
 */
public val LANGUAGE: LanguageFn = LanguageFn.fromRaw(::treeSitterBash)

/**
 * The content of the [`node-types.json`][] file for this grammar.
 *
 * [`node-types.json`]: https://tree-sitter.github.io/tree-sitter/using-parsers#static-node-types
 */
public val NODE_TYPES: String by lazy { readBundledTreeSitterBashResource("node-types.json") }

/**
 * The syntax highlighting query for this grammar.
 *
 * Provides syntax highlighting definitions and captures for Bash constructs.
 */
public val HIGHLIGHT_QUERY: String by lazy { readBundledTreeSitterBashResource("queries/highlights.scm") }
