plugins {
    id("java-platform")
    id("maven-publish")
    id("org.jetbrains.dokka")
}

group = "io.github.jimlyas"
version = "0.1.0"

tasks.withType(org.jetbrains.dokka.gradle.DokkaMultiModuleTask::class.java).configureEach {
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

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("arc") {
                group = this@afterEvaluate.group
                version = this@afterEvaluate.version.toString()
                artifactId = "arc"
                from(components["javaPlatform"])

                pom {
                    name.set("arc")
                    description.set("A concise description of my library")
                    url.set("https://jimlyas.github.io/arc/")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("jimlyas")
                            name.set("Jimly Asshiddiqy")
                            email.set("gmail@jimlyas.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/jimlyas/arc.git")
                        developerConnection.set("scm:git:ssh://github.com/jimlyas/arc.git")
                        url.set("http://github.com/jimlyas/arc")
                    }
                }
            }
        }
    }
}

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "maven-publish")

    group = "io.github.jimlyas"
    version = "0.1.0"

    configure<com.android.build.gradle.LibraryExtension> {
        namespace = "arc.$name"
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

    afterEvaluate {
        publishing {
            publications {
                val moduleName = name[0].toUpperCase() + name.substring(1)
                register<MavenPublication>("arc-$name") {
                    group = this@afterEvaluate.group
                    version = this@afterEvaluate.version.toString()
                    artifactId = "arc-${this@afterEvaluate.name}"

                    pom {
                        name.set("arc-$name")
                        description.set("$moduleName module of Arc library")
                        url.set("https://jimlyas.github.io/arc/")
                        licenses {
                            license {
                                name.set("MIT License")
                                url.set("https://opensource.org/licenses/MIT")
                            }
                        }
                        developers {
                            developer {
                                id.set("jimlyas")
                                name.set("Jimly Asshiddiqy")
                                email.set("gmail@jimlyas.com")
                            }
                        }
                        scm {
                            connection.set("scm:git:git://github.com/jimlyas/arc.git")
                            developerConnection.set("scm:git:ssh://github.com/jimlyas/arc.git")
                            url.set("http://github.com/jimlyas/arc")
                        }
                    }
                }
            }
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