package io.github.kotlinmania.treesitterbash

private val nativeLibraryLoaded: Unit = run { System.loadLibrary("tree-sitter-bash") }

private external fun nativeTreeSitterBashLanguagePointer(): Long

internal actual fun nativeLanguagePointer(): Long {
    nativeLibraryLoaded
    return nativeTreeSitterBashLanguagePointer()
}
