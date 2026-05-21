// port-lint: source bindings/rust/lib.rs
package io.github.kotlinmania.treesitterbash

/**
 * Bash language support for the [tree-sitter](https://tree-sitter.github.io/) parsing library.
 *
 * Typically, use the [LANGUAGE] constant to add this language to a tree-sitter parser
 * and then parse some code:
 *
 * ```kotlin
 * val code = """
 * echo "hello world!"
 * """
 * val parser = Parser()
 * parser.setLanguage(LANGUAGE.into())
 * val tree = parser.parse(code, null)!!
 * check(!tree.rootNode().hasError())
 * ```
 */

// Tracking ledger. The upstream Rust module is parceled across these files in this same
// package, one item per file, in upstream declaration order:
//   - TreeSitterBash.kt    — the bundled grammar entry-point declaration
//   - Language.kt          — the LANGUAGE constant
//   - NodeTypes.kt         — the NODE_TYPES constant
//   - HighlightQuery.kt    — the HIGHLIGHT_QUERY constant
//   - Resources.kt         — the bundled-resource reader used by NODE_TYPES and HIGHLIGHT_QUERY
//
// The inline upstream test module is mirrored under commonTest as LibTest.kt.
