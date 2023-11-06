import com.android.build.gradle.LibraryExtension
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-platform")
    id("maven-publish")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.dokka")
}

group = "io.github.jimlyas"
version = "0.1.0"

tasks.withType<DokkaMultiModuleTask>().configureEach {
    moduleName.set("ARC")
    outputDirectory.set(file("$rootDir/docs/src/api"))
    pluginsMapConfiguration.set(
        mapOf(
            "org.jetbrains.dokka.base.DokkaBase" to """{
                    "footerMessage": "Copyright © 2022-2023 jimlyas. All rights reserved.",
                     "customAssets" : ["${file("logo-icon.svg")}"]
                 }"""
        )
    )
}

publishing {
    publications {
        create<MavenPublication>("arcPlatform") {
            from(components["javaPlatform"])
        }
    }
}

configure<MavenPublishBaseExtension> {
    coordinates(project.group.toString(), "ARC", project.version.toString())
    pom {
        name.set("ARC")
        description.set("Arc Java Platform for managing modules version")
    }
}

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.vanniktech.maven.publish")
    apply(plugin = "org.jetbrains.dokka")

    val baseRepositoryURL = "https://github.com/jimlyas/arc/blob/main/modules/${project.name}"

    group = "io.github.jimlyas"
    version = "0.1.0"

    configure<LibraryExtension> {
        namespace = "arc.$name"
        compileSdk = 33

        defaultConfig {
            minSdk = 21
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
            debug {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        publishing {
            multipleVariants("full") {
                allVariants()
                withSourcesJar()
            }
        }

        compileOptions {
            sourceCompatibility = VERSION_17
            targetCompatibility = VERSION_17
        }

        buildFeatures {
            viewBinding = true
            buildConfig = false
        }

        lint {
            abortOnError = false
            disable += "Instantiatable"
        }
    }

    configure<MavenPublishBaseExtension> {
        coordinates(
            project.group.toString(),
            project.name.toString(),
            project.version.toString()
        )
        pom {
            name.set(project.name)
            description.set("ARC ${project.name} module library")
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions { freeCompilerArgs += "-Xcontext-receivers" }
    }

    tasks.withType<DokkaTaskPartial>().configureEach {
        dokkaSourceSets {
            getByName("main") {
                moduleName.set(project.name[0].uppercaseChar() + project.name.substring(1))
                moduleVersion.set(project.version.toString())
                reportUndocumented.set(false)
                skipDeprecated.set(false)
                skipEmptyPackages.set(true)
                suppressObviousFunctions.set(true)
                suppressInheritedMembers.set(true)
                jdkVersion.set(17)
                noStdlibLink.set(false)
                noJdkLink.set(false)
                noAndroidSdkLink.set(false)
                sourceRoots.setFrom(file("$projectDir/src/main/kotlin"))
                includes.setFrom(files("$projectDir/packages.md"))
                sourceLink {
                    localDirectory.set(file("src/main/kotlin"))
                    remoteUrl.set(uri("${baseRepositoryURL}/src/main/kotlin").toURL())
                    remoteLineSuffix.set("#L")
                }
                pluginsMapConfiguration.set(
                    mapOf(
                        "org.jetbrains.dokka.base.DokkaBase" to """{ 
                    "footerMessage": "Copyright © 2022-2023 jimlyas. All rights reserved.",
                     "customAssets" : ["${file("${rootProject.path}/logo-icon.svg")}"]
                 }"""
                    )
                )
            }
        }
    }
}