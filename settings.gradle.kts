pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins { kotlin("multiplatform") version "2.3.21" }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "tree-sitter-bash-kotlin"

// Workspace composite: the io.github.kotlinmania:tree-sitter-language-kotlin
// sibling that defines `LanguageFn` / `LanguageProvider` lives next to this
// repo but is not yet published to Maven Central. When a checkout of the
// sibling is present alongside (i.e. the kotlinmania workspace layout that
// every developer has locally), use it via Gradle's composite-build
// substitution so `./gradlew build` resolves without a Maven publish.
val treeSitterLanguageLocal = file("../tree-sitter-language-kotlin")
if (treeSitterLanguageLocal.exists()) {
    includeBuild(treeSitterLanguageLocal) {
        dependencySubstitution {
            substitute(module("io.github.kotlinmania:tree-sitter-language-kotlin"))
                .using(project(":"))
        }
    }
}
