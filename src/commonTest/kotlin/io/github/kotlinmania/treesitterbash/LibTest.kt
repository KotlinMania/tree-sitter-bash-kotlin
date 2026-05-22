// port-lint: source lib.rs
package io.github.kotlinmania.treesitterbash

import io.github.kotlinmania.treesitter.Parser
import kotlin.test.Test

class LibTest {
    @Test
    fun testCanLoadGrammar() {
        val parser = Parser()
        parser.setLanguage(LANGUAGE.into())
            ?: error("Error loading Bash parser")
    }
}
