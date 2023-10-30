import com.android.build.gradle.LibraryExtension
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.lang.System.getenv
import java.net.URI

plugins {
    id("java-platform")
    id("org.jetbrains.dokka")
    id("maven-publish")
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

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.dokka")

    val baseRepositoryURL = "https://github.com/jimlyas/arc/blob/main/modules/${project.name}"

    group = "io.github.jimlyas"
    version = "0.1.0"

    configure<LibraryExtension> {
        namespace = "arc.$name"
        compileSdk = 34

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
            singleVariant("debug")
            singleVariant("release")
            multipleVariants("full") { allVariants() }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
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

    publishing {
        publications {
            register<MavenPublication>("debug") {
                afterEvaluate {
                    this@register.groupId = group.toString()
                    this@register.artifactId = "arc-$name"
                    this@register.version = version.toString()
                    from(components["debug"])
                }
            }
        }

        repositories {
            maven {
                name = "staging"
                url = URI("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = getenv("MAVEN_USERNAME")
                    password = getenv("MAVEN_PASSWORD")
                }
            }
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