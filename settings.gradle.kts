pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven {
            url = uri("https://maven.pkg.github.com/karthik-pro-engr/architecting-state")
            credentials {
                username = providers.gradleProperty("gpr.user").orNull ?: System.getenv("GITHUB_ACTOR")
                password = providers.gradleProperty("gpr.token").orNull ?: System.getenv("GITHUB_TOKEN")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            // Map plugin id -> exact published Maven coordinates (force the module Gradle should download)
            if (requested.id.id == "karthik.pro.engr.android.application") {
                useModule("karthik.pro.engr:android.application.gradle.plugin:${requested.version}")
            }
            if (requested.id.id == "karthik.pro.engr.android.library") {
                useModule("karthik.pro.engr:android.library.gradle.plugin:${requested.version}")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "algo-compose"
include(":app")