plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlin.serialization).apply(false)
    alias(libs.plugins.buildKonfig).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.detekt).apply(false)
    alias(libs.plugins.ktlint).apply(false)
}
