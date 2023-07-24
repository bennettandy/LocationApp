// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version Kotlin.version apply false
    id("com.google.dagger.hilt.android") version DaggerHilt.version apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.4.0" apply false
    id("org.jetbrains.kotlin.jvm") version Kotlin.version apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent

    repositories {
        // Required to download KtLint
        mavenCentral()
    }

    // Optionally configure plugin
//    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
//        debug.set(true)
//    }
}
