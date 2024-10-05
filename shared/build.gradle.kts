import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions.jvmTarget.set(JvmTarget.JVM_18)
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project.dependencies.platform(libs.supabase.bom))
            api(libs.supabase.postgrest.kt)
            api(libs.supabase.gotrue.kt)
            api(libs.supabase.realtime.kt)

            api(libs.ktor.client.core)
            api(libs.ktor.client.logging)
            api(libs.ktor.serialization.kotlinx.json)
            api(libs.ktor.client.resources)
            api(libs.ktor.client.auth)

            api(libs.androidx.lifecycle.viewmodel)

            api(libs.koin.core)
            api(libs.koin.test)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonTest.dependencies {
            api(libs.kotlin.test)
        }
    }
}

buildkonfig {
    packageName = Configs.APPLICATION_ID

    val props = Properties()
    try {
        props.load(file(project.rootProject.file("local.properties")).inputStream())
    } catch (_: Exception) {
        // TODO: Handle exception
    }

    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING,
            "SUPABASE_API_KEY",
            props["supabase_api_key"]?.toString() ?: "https://fakeapi.com"
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "SUPABASE_URL",
            props["supabase_url"]?.toString() ?: "https://fakeapi.com"
        )
    }
}

android {
    namespace = Configs.APPLICATION_ID
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK
    }

    compileOptions {
        sourceCompatibility = Configs.sourceCompatibility
        targetCompatibility = Configs.targetCompatibility
    }
}
