plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.androidConfiguration)
}

android {
    namespace = NyampurConfig.ANDROID_APPLICATION_ID
    defaultConfig {
        applicationId = NyampurConfig.ANDROID_APPLICATION_ID
        versionCode = NyampurConfig.VERSION_CODE
        versionName = NyampurConfig.VERSION_NAME
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.bundles.androidCore)
    implementation(libs.koin.android)
}
