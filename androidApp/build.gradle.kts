plugins {
    alias(fall.plugins.androidApplication)
    alias(fall.plugins.kotlinAndroid)
    alias(fall.plugins.androidConfiguration)
}

dependencies {
    implementation(project(":shared"))
    implementation(fall.bundles.androidCore)

    // Glide
    implementation(fall.glide)

    // Material
    implementation(fall.material)

    // Koin
    implementation(fall.koin.android)
}