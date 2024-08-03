plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "${Configs.APPLICATION_ID}.android"
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        applicationId = "${Configs.APPLICATION_ID}.android"
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.COMPILE_SDK
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = Configs.sourceCompatibility
        targetCompatibility = Configs.targetCompatibility
    }

    kotlinOptions {
        jvmTarget = Configs.JVM_TARGET
    }
}

dependencies {
    implementation(projects.shared)

    // AndroidX
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Glide
    implementation(libs.glide)

    // Material
    implementation(libs.material)

    // Koin
    implementation(libs.koin.android)
}