pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.parchmentmc.org")
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

include("common")

include("fabric")

// This fork targets Fabric 1.20.1. NeoForge sources remain upstream-only.

rootProject.name = "accessories_compat"//rootProject.property("mod_name")




