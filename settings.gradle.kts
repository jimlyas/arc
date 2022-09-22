rootProject.name = "arc"
include(":sample", ":modules", ":modules:data", ":modules:presentation", ":modules:utilities")
enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        maven { setUrl("https://jitpack.io") }
        mavenCentral()
        google()
    }
}