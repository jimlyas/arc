@Suppress(
    "DSL_SCOPE_VIOLATION",
    "MISSING_DEPENDENCY_CLASS",
    "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED"
)
plugins {
    alias(libs.plugins.app) apply false
    alias(libs.plugins.lib) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.dokka) apply false
}

tasks.register("clean", Delete::class) { delete(rootProject.buildDir) }