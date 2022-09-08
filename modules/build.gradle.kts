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

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "maven-publish")

    group = "com.jimlyas.arc"

    configure<com.android.build.gradle.LibraryExtension> {
        namespace = "jimlyas.arc.$name"
        compileSdk = 31
        defaultConfig {
            minSdk = 21
            targetSdk = 31
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
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        publishing {
            singleVariant("debug") {
                withSourcesJar()
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
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

    tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
        dokkaSourceSets {
            getByName("main") {
                moduleName.set(project.name[0].toUpperCase() + project.name.substring(1))
                reportUndocumented.set(false)
                skipDeprecated.set(false)
                skipEmptyPackages.set(true)
                suppressObviousFunctions.set(true)
                suppressInheritedMembers.set(true)
                jdkVersion.set(11)
                noStdlibLink.set(false)
                noJdkLink.set(false)
                noAndroidSdkLink.set(false)
                sourceRoots.setFrom(file("$projectDir/src/main/java"))
                includes.setFrom(files("$projectDir/packages.md"))
                sourceLink {
                    localDirectory.set(file("src/main/java"))
                    remoteUrl.set(uri("https://github.com/jimlyas/arc/blob/main/modules/${project.name}/src/main/java").toURL())
                    remoteLineSuffix.set("#L")
                }
                pluginsMapConfiguration.set(
                    mapOf(
                        "org.jetbrains.dokka.base.DokkaBase" to """{ 
                    "footerMessage": "© Jimly A." 
                 }"""
                    )
                )
            }
        }
    }
}