pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()

    }
    plugins {


        id("com.android.application") version "8.9.2"
        id("com.android.library") version "8.10.0"
        id("org.jetbrains.kotlin.android") version "2.1.0"
        id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"
        id("com.google.devtools.ksp") version "2.1.0-1.0.29"

        id("com.google.dagger.hilt.android") version "2.55"

    }

}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}


rootProject.name = "test_V2"
include(":app")
 