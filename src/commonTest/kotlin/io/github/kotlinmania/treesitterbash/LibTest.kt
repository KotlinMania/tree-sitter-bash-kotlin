// port-lint: source bindings/rust/lib.rs
package io.github.kotlinmania.treesitterbash

import io.github.kotlinmania.treesitter.Parser
import kotlin.test.Test

/**
 * Translated from `#[cfg(test)] mod tests` in `bindings/rust/lib.rs`.
 */
class LibTest {
    @Test
    fun testCanLoadGrammar() {
        val parser = Parser()
        parser.setLanguage(LANGUAGE.into())
            ?: error("Error loading Bash parser")
    }
}
