import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    alias(libs.plugins.googlDevToolsKSP)

    alias(libs.plugins.androidx.room)
    alias(libs.plugins.serializationPlugins)

    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.googleServices)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

//    iosX64()
    iosArm64()
    iosSimulatorArm64()

//    listOf(
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "ComposeApp"
//            isStatic = true
//        }
//    }

    cocoapods {
        // Required properties
        // Specify the required Pod version here
        // Otherwise, the Gradle project version is used
        version = "1.0"
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
        podfile = project.file("../iosApp/Podfile")


        // Optional properties
        // Configure the Pod name here instead of changing the Gradle project name
        name = "ComposeApp"

        ios.deploymentTarget = "16.0"

//        pod("PurchasesHybridCommon") {
//            version = libs.versions.purchases.common.get()
//            extraOpts += listOf("-compiler-option", "-fmodules")
//        }
//
//        pod("PurchasesHybridCommonUI") {
//            version = libs.versions.purchases.common.get()
//            extraOpts += listOf("-compiler-option", "-fmodules")
//        }

        pod("RevenueCat") {
            version = "5.57.1"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }


        pod("FirebaseAuth") {
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        pod("FirebaseFirestore") {
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        pod("GoogleSignIn") {
            extraOpts += listOf("-compiler-option", "-fmodules")
        }


        framework {
            baseName = "ComposeApp"
            isStatic = false
            transitiveExport = false // This is default.

            binaryOption("bundleId", "org.example.ComposeApp")
            linkerOpts.add("-lsqlite3")
            linkerOpts("-F${project.buildDir}/cocoapods/framework")
            linkerOpts("-framework", "PurchasesHybridCommon")
            linkerOpts("-framework", "PurchasesHybridCommonUI")

        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.androidx.room.sqlite.wrapper)
            implementation(libs.koin.android)

            implementation(libs.googleid)

            implementation(libs.ktor.client.android)


            val bom = project.dependencies.platform("com.google.firebase:firebase-bom:33.1.0")
            implementation(bom)
            implementation(libs.google.firebase.auth)
            implementation(libs.google.services.auth)
            implementation(libs.firebase.firestore)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.preview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.navigation.compose)

            implementation(libs.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            implementation(libs.kotlinx.serialization.json)

            implementation(libs.kotlinx.datetime)

            api(libs.koin.core)

            implementation(libs.koin.compose.viewmodel)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.firebase.common)

            implementation(libs.jetbrains.navigation3.ui)


//            implementation("com.revenuecat.purchases:purchases-kmp-core:2.2.17+17.26.1")

            implementation(libs.purchases.core)
            implementation(libs.purchases.either)     // Optional
            implementation(libs.purchases.result)
            implementation(libs.purchases.ui)

            implementation(libs.gitlive.firebase.firestore)

            implementation(libs.compose.multiplatform.media.player)

        }
//        commonTest.dependencies {
//            implementation(libs.kotlin.test)
//        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }


        named { it.lowercase().startsWith("ios") }.configureEach {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.garam.qook"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.garam.qook"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 19
        versionName = "1.0"

        buildConfigField(
            "String",
            "RevenueCat_API_KEY",
            localProperties.getProperty("RevenueCat_API_KEY") ?: "\"test_wcAowsgxNEWVcvelxYnyfwLefzp\""
        )
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
//    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}

//tasks.register("syncAndRun", Exec::class) {
//    dependsOn(tasks.getByName("syncFramework"))
//    workingDir = rootDir.resolve("iosApp")
//    commandLine("sh", "-c", "xcodebuild -showsdks | grep iphoneos && open ${project.name}.xcworkspace")
//}

tasks.register("generateXcodeProject") {
    dependsOn(tasks.getByName("podInstall")) // Cocoapods를 사용하는 경우
}



