pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // ESTE ES EL REPOSITORIO DE MAPBOX
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
        }
    }
}

rootProject.name = "AppUbicacion2"
include(":app")