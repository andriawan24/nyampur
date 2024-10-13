plugins {
    alias(libs.plugins.configuration.android)
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
    implementation(libs.bundles.android.core)
    implementation(libs.koin.android)
}
