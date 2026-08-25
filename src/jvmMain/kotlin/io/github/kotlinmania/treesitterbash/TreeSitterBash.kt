package io.github.kotlinmania.treesitterbash

private val nativeLibraryLoaded: Result<Unit> by lazy {
    runCatching { System.loadLibrary("tree-sitter-bash") }
}

private external fun nativeTreeSitterBashLanguagePointer(): Long

internal actual fun nativeLanguagePointer(): Long {
    nativeLibraryLoaded.getOrThrow()
    return nativeTreeSitterBashLanguagePointer()
}
