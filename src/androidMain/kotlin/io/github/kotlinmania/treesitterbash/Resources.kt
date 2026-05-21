// port-lint: ignore
package io.github.kotlinmania.treesitterbash

internal actual fun readBundledTreeSitterBashResource(name: String): String {
    val path = "io/github/kotlinmania/treesitterbash/$name"
    val loader = Thread.currentThread().contextClassLoader
        ?: TreeSitterBashResourcesMarker::class.java.classLoader
    val stream = loader.getResourceAsStream(path)
        ?: error("Bundled tree-sitter-bash resource not found on classpath: $path")
    return stream.bufferedReader(Charsets.UTF_8).use { it.readText() }
}

private class TreeSitterBashResourcesMarker
