// port-lint: source bindings/rust/build.rs
package io.github.kotlinmania.treesitterbash

/**
 * Build configuration utilities matching upstream Cargo build script.
 */
internal object Build {
    /**
     * Builder mimicking `cc::Build` compiler configuration.
     */
    class CcBuild {
        var std: String? = null
        val includeDirs: MutableList<String> = mutableListOf()
        val flags: MutableList<String> = mutableListOf()
        val files: MutableList<String> = mutableListOf()

        fun std(standard: String): CcBuild {
            this.std = standard
            return this
        }

        fun include(dir: String): CcBuild {
            includeDirs.add(dir)
            return this
        }

        fun flagIfSupported(flag: String): CcBuild {
            flags.add(flag)
            return this
        }

        fun flag(flag: String): CcBuild {
            flags.add(flag)
            return this
        }

        fun file(filePath: String): CcBuild {
            files.add(filePath)
            return this
        }

        fun compile(libName: String): String = "cargo:compile=$libName"
    }

    /**
     * Configures C compiler flags and source paths for building tree-sitter-bash.
     *
     * @param srcDir path to C sources directory
     * @param isMsvc whether targeting MSVC environment
     * @return list of configured rerun and compile instructions
     */
    fun configure(srcDir: String = "src", isMsvc: Boolean = false): List<String> {
        val outputs = mutableListOf<String>()
        val cConfig = CcBuild()
        cConfig
            .std("c11")
            .include(srcDir)
            .flagIfSupported("-Wno-unused-value")
        if (isMsvc) {
            cConfig.flag("-utf-8")
        }
        val parserPath = "$srcDir/parser.c"
        cConfig.file(parserPath)
        outputs.add("cargo:rerun-if-changed=$parserPath")

        val scannerPath = "$srcDir/scanner.c"
        cConfig.file(scannerPath)
        outputs.add("cargo:rerun-if-changed=$scannerPath")

        outputs.add(cConfig.compile("tree-sitter-bash"))
        return outputs
    }

    /**
     * Executes the build script logic.
     */
    fun main(srcDir: String = "src", isMsvc: Boolean = false) {
        val srcDir = srcDir

        val cConfig = CcBuild()
        cConfig
            .std("c11")
            .include(srcDir)
            .flagIfSupported("-Wno-unused-value")

        if (isMsvc) {
            cConfig.flag("-utf-8")
        }

        val parserPath = "$srcDir/parser.c"
        cConfig.file(parserPath)
        println("cargo:rerun-if-changed=$parserPath")

        val scannerPath = "$srcDir/scanner.c"
        cConfig.file(scannerPath)
        println("cargo:rerun-if-changed=$scannerPath")

        cConfig.compile("tree-sitter-bash")
    }
}
