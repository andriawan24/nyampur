plugins {
    alias(libs.plugins.configuration.android)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = NyampurConfig.ANDROID_APPLICATION_ID
    defaultConfig {
        applicationId = NyampurConfig.ANDROID_APPLICATION_ID
        versionCode = NyampurConfig.VERSION_CODE
        versionName = NyampurConfig.VERSION_NAME
    }
}

detekt {
    toolVersion = "1.23.7"

    source.setFrom("src/main/java")
    config.setFrom(rootProject.file("detekt/config.yml"))

    parallel = true
    autoCorrect = true
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.koin.android)
}
