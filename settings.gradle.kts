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
            url = uri("https://maven.pkg.github.com/karthik-pro-engr/build-logic")
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
                useModule("karthik.pro.engr:android-application-plugin:${requested.version}")
            }
            if (requested.id.id == "karthik.pro.engr.android.library") {
                useModule("karthik.pro.engr:android-library-plugin:${requested.version}")
            }


            if (requested.id.id == "com.google.gms.google-services") {
                // pick the plugin artifact coordinate and version you want to use:
                useModule("com.google.gms:google-services:4.4.0")
            }
            if (requested.id.id == "com.google.firebase.crashlytics") {
                // pick the plugin artifact coordinate and version you want to use:
                useModule("com.google.firebase:firebase-crashlytics-gradle:3.0.6")
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