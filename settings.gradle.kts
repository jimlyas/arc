rootProject.name = "arc"
include(":sample", ":modules", ":modules:data", ":modules:presentation", ":modules:utilities")
listOf("TYPESAFE_PROJECT_ACCESSORS").forEach { enableFeaturePreview(it) }

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://jitpack.io")
        mavenCentral()
        google()
    }
}