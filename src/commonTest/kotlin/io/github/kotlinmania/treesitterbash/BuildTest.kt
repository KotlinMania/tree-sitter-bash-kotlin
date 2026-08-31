// port-lint: tests tree-sitter-bash/bindings/rust/build.rs
package io.github.kotlinmania.treesitterbash

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BuildTest {
    @Test
    fun configureGeneratesExpectedPaths() {
        val outputs = Build.configure("src", false)
        assertEquals(3, outputs.size)
        assertTrue(outputs.contains("cargo:rerun-if-changed=src/parser.c"))
        assertTrue(outputs.contains("cargo:rerun-if-changed=src/scanner.c"))
        assertTrue(outputs.contains("cargo:compile=tree-sitter-bash"))
    }

    @Test
    fun configureMsvcGeneratesExpectedPaths() {
        val outputs = Build.configure("src", true)
        assertEquals(3, outputs.size)
    }

    @Test
    fun mainRunsWithoutError() {
        Build.main("src", false)
        Build.main("custom/src", true)
    }
}
