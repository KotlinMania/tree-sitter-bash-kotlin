// port-lint: tests bindings/rust/lib.rs
package io.github.kotlinmania.treesitterbash

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Smoke tests for the commonMain surface: bundled resources are reachable
 * on every Kotlin target via the generated [BUNDLED_TREE_SITTER_BASH_RESOURCES]
 * map, and [LANGUAGE] is a non-null [LanguageFn].
 */
class LibTest {
    @Test
    fun nodeTypesJsonIsReachableFromCommonMain() {
        val text = NODE_TYPES
        assertTrue(text.isNotEmpty(), "NODE_TYPES should not be empty")
        assertTrue(text.trimStart().startsWith("["), "node-types.json begins with a JSON array")
    }

    @Test
    fun highlightQueryIsReachableFromCommonMain() {
        val text = HIGHLIGHT_QUERY
        assertTrue(text.isNotEmpty(), "HIGHLIGHT_QUERY should not be empty")
    }

    @Test
    fun bundledResourcesMapContainsBothEntries() {
        assertEquals(2, BUNDLED_TREE_SITTER_BASH_RESOURCES.size)
        assertNotNull(BUNDLED_TREE_SITTER_BASH_RESOURCES["node-types.json"])
        assertNotNull(BUNDLED_TREE_SITTER_BASH_RESOURCES["queries/highlights.scm"])
    }

    @Test
    fun testCanLoadGrammar() {
        assertNotNull(LANGUAGE)
        assertNotNull(LANGUAGE.intoRaw())
    }
}
