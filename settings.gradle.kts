rootProject.name = "arc"
include(":sample", ":modules")
enableFeaturePreview("VERSION_CATALOGS")

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