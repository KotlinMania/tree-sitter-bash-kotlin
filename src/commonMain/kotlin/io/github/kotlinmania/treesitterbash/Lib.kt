// port-lint: source lib.rs
package io.github.kotlinmania.treesitterbash

/**
 * This module provides Bash language support for the [tree-sitter] parsing library.
 *
 * Typically, you will use the [LANGUAGE] constant to add this language to a
 * tree-sitter [Parser], and then use the parser to parse some code:
 *
 * ```kotlin
 * import io.github.kotlinmania.treesitter.Parser
 *
 * val code = """
 * echo "hello world!"
 * """
 * val parser = Parser()
 * parser.setLanguage(LANGUAGE.into())
 *     ?: error("Error loading Bash parser")
 * val tree = parser.parse(code, null)!!
 * check(!tree.rootNode().hasError())
 * ```
 *
 * @see Parser the tree-sitter parser type, supplied by the io.github.kotlinmania:tree-sitter-kotlin sibling
 * @see <a href="https://tree-sitter.github.io/">tree-sitter</a>
 */

// Tracking ledger. The upstream Rust module is parceled across these files in this same
// package, one item per file, in upstream declaration order:
//   - TreeSitterBash.kt    — the bundled grammar entry-point declaration
//   - Language.kt          — the LANGUAGE constant
//   - NodeTypes.kt         — the NODE_TYPES constant
//   - HighlightQuery.kt    — the HIGHLIGHT_QUERY constant
//   - Resources.kt         — the bundled-resource reader used by NODE_TYPES and HIGHLIGHT_QUERY
//   - Build.kt             — ledger for the upstream Cargo build.rs (native compilation lives in build.gradle.kts)
//
// The inline upstream test module is mirrored under commonTest as LibTest.kt.

internal object Lib
