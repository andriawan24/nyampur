pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("fall") {
            from(files("dependencies/gradle/libs.versions.toml"))
        }
    }

    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Nyampur"
include(":androidApp")
include(":shared")