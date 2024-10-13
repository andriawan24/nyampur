import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(fall.plugins.kotlinMultiplatform)
    alias(fall.plugins.androidLibrary)
    alias(fall.plugins.buildKonfig)
    alias(fall.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions.jvmTarget.set(JvmTarget.JVM_1_8)
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
            api(project.dependencies.platform(fall.supabase.bom))
            api(fall.supabase.postgrest.kt)
            api(fall.supabase.gotrue.kt)
            api(fall.supabase.realtime.kt)

            api(fall.ktor.client.core)
            api(fall.ktor.client.logging)
            api(fall.ktor.serialization.kotlinx.json)
            api(fall.ktor.client.resources)
            api(fall.ktor.client.auth)

            api(fall.androidx.lifecycle.viewmodel)

            api(fall.koin.core)
            api(fall.koin.test)
        }

        androidMain.dependencies {
            implementation(fall.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(fall.ktor.client.darwin)
        }

        commonTest.dependencies {
            api(fall.kotlin.test)
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
