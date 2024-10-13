import org.gradle.api.JavaVersion

object Configs {
    const val APPLICATION_ID = "id.nisyafawwaz.nyampur"
    const val ANDROID_APPLICATION_ID = "id.nisyafawwaz.nyampur.android"
    const val MIN_SDK = 24
    const val TARGET_SDK = 35
    const val COMPILE_SDK = 35
    const val JVM_TARGET = "18"
    val sourceCompatibility = JavaVersion.VERSION_18
    val targetCompatibility = JavaVersion.VERSION_18
}