// port-lint: source bindings/rust/lib.rs
package io.github.kotlinmania.treesitterbash

import io.github.kotlinmania.treesitterlanguage.LanguageFn

/**
 * The tree-sitter [LanguageFn] for this grammar.
 */
val LANGUAGE: LanguageFn = LanguageFn.fromRaw(::treeSitterBash)
