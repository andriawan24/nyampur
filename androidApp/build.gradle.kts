import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.util.Properties

val props = Properties()
try {
    props.load(file(project.rootProject.file("local.properties")).inputStream())
} catch (_: Exception) {
    // TODO: Handle exception
}

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

    buildTypes {
        debug {
            isDebuggable = true
            isShrinkResources = false
            isMinifyEnabled = false
            buildConfigField("String", "GOOGLE_CLIENT_WEB_ID", "\"${props["google_client_id"]?.toString() ?: "https://fakeapi.com"}\"")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        release {
            isDebuggable = false
            isShrinkResources = true
            isMinifyEnabled = true
            buildConfigField("String", "GOOGLE_CLIENT_WEB_ID", "\"${props["google_client_id"]?.toString() ?: "https://fakeapi.com"}\"")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

detekt {
    toolVersion = libs.versions.detekt.get()
    source.setFrom(
        "src/main/java",
        "src/main/kotlin",
        "src/test/java",
        "src/test/kotlin",
    )
    config.setFrom(rootProject.file("detekt/config.yml"))
    baseline = rootProject.file("detekt/baseline.xml")
    parallel = true
    autoCorrect = true
    buildUponDefaultConfig = true
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    android.set(true)
    ignoreFailures.set(false)
    reporters {
        reporter(ReporterType.HTML)
        reporter(ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/generated/**")
        exclude("**/kotlin/**")
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.koin.android)
    implementation(libs.androidx.credentials)
    implementation(libs.googleid)
    implementation(libs.androidx.credentials.play.services.auth)
}
