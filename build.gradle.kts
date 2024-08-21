// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    id("com.android.library") version "8.2.2" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.22" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.21"

}
buildscript{
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies{
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.51")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.2.1")

    }

}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}