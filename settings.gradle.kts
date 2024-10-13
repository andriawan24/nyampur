pluginManagement {
    includeBuild("dependencies")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    // TODO: Revert to root project
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
