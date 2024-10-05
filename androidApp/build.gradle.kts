plugins {
    alias(fall.plugins.androidApplication)
    alias(fall.plugins.kotlinAndroid)
}

android {
    namespace = Configs.ANDROID_APPLICATION_ID
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        applicationId = Configs.ANDROID_APPLICATION_ID
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK
        versionCode = 1
        versionName = "0.0.1"
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
    implementation(project(":shared"))

    // AndroidX
    implementation(fall.androidx.appcompat)
    implementation(fall.androidx.activity)
    implementation(fall.androidx.constraintlayout)

    // Swipe Refresh Layout
    implementation(fall.androidx.swiperefreshlayout)

    // Glide
    implementation(fall.glide)

    // Material
    implementation(fall.material)

    // Koin
    implementation(fall.koin.android)
}