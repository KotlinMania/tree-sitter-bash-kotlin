// port-lint: source bindings/rust/build.rs
package io.github.kotlinmania.treesitterbash

/**
 * Build configuration utilities matching upstream Cargo build script.
 */
internal object Build {
    /**
     * Configures C compiler flags and source paths for building tree-sitter-bash.
     *
     * @param srcDir path to C sources directory
     * @param isMsvc whether targeting MSVC environment
     * @return list of configured rerun and compile instructions
     */
    fun configure(srcDir: String = "src", isMsvc: Boolean = false): List<String> {
        val parserPath = "$srcDir/parser.c"
        val scannerPath = "$srcDir/scanner.c"
        val flags = mutableListOf("-std=c11", "-I$srcDir", "-Wno-unused-value")
        if (isMsvc) {
            flags.add("-utf-8")
        }
        return listOf(
            "cargo:rerun-if-changed=$parserPath",
            "cargo:rerun-if-changed=$scannerPath",
            "cargo:compile=tree-sitter-bash",
        )
    }

    /**
     * Executes the build script logic.
     */
    fun main(srcDir: String = "src", isMsvc: Boolean = false) {
        val outputs = configure(srcDir, isMsvc)
        for (output in outputs) {
            println(output)
        }
    }
}
