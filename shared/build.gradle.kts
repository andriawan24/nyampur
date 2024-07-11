import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
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
            api(libs.ktor.client.cio)
        }

        commonTest.dependencies {
            api(libs.kotlin.test)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

buildkonfig {
    packageName = "id.nisyafawwaz.nyampur"

    val props = Properties()
    try {
        props.load(file(project.rootProject.file("local.properties")).inputStream())
    } catch (_: Exception) {
        // Handle exception
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
    namespace = "id.nisyafawwaz.nyampur"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
