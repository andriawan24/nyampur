import org.gradle.api.JavaVersion

object Configs {
    const val APPLICATION_ID = "id.nisyafawwaz.nyampur"
    const val MIN_SDK = 24
    const val COMPILE_SDK = 34
    const val JVM_TARGET = "1.8"
    val sourceCompatibility = JavaVersion.VERSION_1_8
    val targetCompatibility = JavaVersion.VERSION_1_8
}