pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
plugins {
    id("de.fayard.refreshVersions") version "0.40.2"
}
rootProject.name = "zemogatest"
include(":app")
include(":data")
include(":features:posts")
include(":features:shared")
include(":features:postdetail")
include(":domain")
