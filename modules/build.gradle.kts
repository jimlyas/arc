import com.android.build.gradle.LibraryExtension
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {
	id("java-platform")
	id("maven-publish")
	id("com.vanniktech.maven.publish")
	id("org.jetbrains.dokka")
}

group = "io.github.jimlyas"
version = "0.1.2"

tasks.withType<DokkaMultiModuleTask>().configureEach {
	moduleName.set(rootProject.name)
	outputDirectory.set(file("$rootDir/docs/src/api"))
	pluginsMapConfiguration.set(
		mapOf(
			"org.jetbrains.dokka.base.DokkaBase" to """{
                    "footerMessage": "Copyright © 2022-2024 jimlyas. All rights reserved.",
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

afterEvaluate {
	configure<MavenPublishBaseExtension> {
		coordinates(project.group.toString(), rootProject.name, project.version.toString())
		pom {
			name.set(rootProject.name)
			description.set("Arc Java Platform for managing modules version")
		}
	}
}

dependencies {
	constraints {
		api(projects.modules.data)
		api(projects.modules.presentation)
		api(projects.modules.utilities)
	}
}

subprojects {
	apply(plugin = "com.android.library")
	apply(plugin = "kotlin-android")
	apply(plugin = "com.vanniktech.maven.publish")
	apply(plugin = "org.jetbrains.dokka")

	group = "io.github.jimlyas"
	version = "0.1.2"

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

	afterEvaluate {
		configure<MavenPublishBaseExtension> {
			coordinates(
				this@subprojects.group.toString(),
				"arc-" + this@subprojects.name.toString(),
				this@subprojects.version.toString()
			)
			pom {
				name.set(this@subprojects.name)
				description.set("ARC ${this@subprojects.name} module library")
			}
		}
	}

	tasks.withType<KotlinCompile>().configureEach {
		kotlinOptions { freeCompilerArgs += "-Xcontext-receivers" }
	}

	val repositoryURL = "https://github.com/jimlyas/arc/tree/main/modules/${project.name}/"

	tasks.withType<DokkaTaskPartial>().configureEach {
		moduleName.set(project.name[0].uppercaseChar() + project.name.substring(1))
		moduleVersion.set(project.version.toString())

		dokkaSourceSets {
			named("main") {
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
					remoteUrl.set(URL("${repositoryURL}src/main/kotlin"))
					remoteLineSuffix.set("#L")
				}
				pluginsMapConfiguration.set(
					mapOf(
						"org.jetbrains.dokka.base.DokkaBase" to """{ 
                    "footerMessage": "Copyright © 2022-2024 jimlyas. All rights reserved.",
                     "customAssets" : ["${file("${rootProject.path}/logo-icon.svg")}"]
                 }"""
					)
				)
			}
		}
	}
}