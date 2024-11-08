plugins {
    alias(libs.plugins.configuration.android)
    alias(libs.plugins.ktlint)
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
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
    implementation(libs.koin.android)
}
