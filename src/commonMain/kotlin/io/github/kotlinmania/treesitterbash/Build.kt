// port-lint: source bindings/rust/build.rs
package io.github.kotlinmania.treesitterbash

import io.github.kotlinmania.cc.Build

private class Path(
    private val path: String,
) {
    fun join(other: String): Path = Path(if (path.isEmpty()) other else "$path/$other")

    fun toStr(): Path = this

    fun unwrap(): String = path

    override fun toString(): String = path

    companion object {
        fun new(path: String): Path = Path(path)
    }
}

private fun Build.include(path: Path): Build =
    include(path.unwrap())

private fun Build.file(path: Path): Build =
    file(path.unwrap())

private fun println(pattern: String, arg: Any?) {
    kotlin.io.println(pattern.replace("{}", arg.toString()))
}

public fun main() {
    val srcDir = Path.new("src")

    val cConfig = Build.new()
    cConfig
        .std("c11")
        .include(srcDir)
        .flagIfSupported("-Wno-unused-value")

    val targetEnv = "msvc"
    if (targetEnv == "msvc") {
        cConfig.flag("-utf-8")
    }

    val parserPath = srcDir.join("parser.c")
    cConfig.file(parserPath)
    println("cargo:rerun-if-changed={}", parserPath.toStr().unwrap())

    val scannerPath = srcDir.join("scanner.c")
    cConfig.file(scannerPath)
    println("cargo:rerun-if-changed={}", scannerPath.toStr().unwrap())

    cConfig.compile("tree-sitter-bash")
}
