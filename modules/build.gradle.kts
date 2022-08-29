import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

plugins {
    id("java-platform")
    id("org.jetbrains.dokka")
}

group = "com.github.jimlyas"
version = "0.1.0"

tasks.withType(DokkaMultiModuleTask::class.java).configureEach {
    moduleName.set("ARC")
    outputDirectory.set(buildDir)
    pluginsMapConfiguration.set(
        mapOf(
            "org.jetbrains.dokka.base.DokkaBase" to """{ 
                    "footerMessage": "© Jimly A." 
                 }"""
        )
    )
}