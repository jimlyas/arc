plugins {
    alias(libs.plugins.app) apply false
    alias(libs.plugins.lib) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.dokka) apply false
}

tasks.register("clean", Delete::class) { delete(rootProject.buildDir) }