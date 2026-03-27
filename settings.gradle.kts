pluginManagement {
    includeBuild("build-logic")
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenCentral()
        maven("https://repo.codemc.io/repository/maven-public/")
        maven("https://jitpack.io")
        gradlePluginPortal()
    }
}

rootProject.name = "practice"

include("task1")
include("task2")
include("task3")
include("task4")