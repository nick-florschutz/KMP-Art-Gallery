import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.room)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {

//        iosMain {
//            kotlin.srcDir("build/generated/ksp/metadata")
//        }
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Navigation
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)

            // Coroutines - https://github.com/Kotlin/kotlinx.coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Date & Time - https://github.com/Kotlin/kotlinx-datetime
            implementation(libs.kotlinx.datetime)

            // datastore
            implementation(libs.datastore.preferences)
            implementation(libs.atomicfu)

            // Konnectivity - https://github.com/plusmobileapps/konnectivity
            implementation(libs.konnectivity)

            // Napier Logging - https://github.com/AAkira/Napier
            implementation(libs.napier)

            // Common ViewModel - https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-viewmodel.html
            implementation(libs.lifecycle.viewmodel.compose)

            // Koin DI - https://insert-koin.io/docs/reference/koin-mp/kmp
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.test)

            // Moko Permissions - https://github.com/icerockdev/moko-permissions
            api(libs.permissions.compose) // permissions api + compose extensions
            implementation(libs.permissions.test)

            // Ktorfit -https://github.com/Foso/Ktorfit
            implementation(libs.ktorfit)
            implementation(libs.ktorfit.callconverter)

            // Room Database
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)


        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
            implementation(libs.koin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

android {
    namespace = "kmp.fbk.kmpartgallery"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "kmp.fbk.kmpartgallery"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
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

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    debugImplementation(compose.uiTooling)
//    add("kspCommonMainMetadata", libs.room.compiler)
//    add("kspAndroid", libs.room.compiler)
//    ksp(libs.room.compiler)

    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}


//tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
//    if (name != "kspCommonMainKotlinMetadata") {
//        dependsOn("kspCommonMainKotlinMetadata")
//    }
//}
