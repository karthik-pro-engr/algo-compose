import com.google.firebase.appdistribution.gradle.firebaseAppDistribution
import org.gradle.kotlin.dsl.implementation
import java.util.Base64

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.firebase.appdistribution)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("karthik.pro.engr.android.application") version "1.2.3"
}

android {
    namespace = "com.karthik.pro.engr.algocompose"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.karthik.pro.engr.algocompose"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.4.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("boolean", "ENABLE_APP_DISTRIBUTION", "false")
    }

    signingConfigs {
        create("release") {
            storeFile = file("release.jks")
            storePassword = System.getenv("RELEASE_STORE_PASSWORD")
            keyAlias = System.getenv("RELEASE_KEY_ALIAS")
            keyPassword = System.getenv("RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("boolean", "ENABLE_APP_DISTRIBUTION", "true")
        }
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("boolean", "ENABLE_APP_DISTRIBUTION", "false")
        }
        create("beta") {
            // Start from release so beta is signed the same and uses release-like settings
            initWith(getByName("release"))
            buildConfigField("boolean", "ENABLE_APP_DISTRIBUTION", "true")

            // ensure beta is signed with release keystore so it's valid for App Distribution
            signingConfig = signingConfigs.getByName("release")

            // if you want fast iteration, you can disable minify for beta:
            isMinifyEnabled = false

            // fallback to release resources/configs if some plugin expects release
//            matchingFallbacks += listOf("release")
            versionNameSuffix = "-beta"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

// ---- Firebase App Distribution block (module-level) ----
firebaseAppDistribution {
    // required: your app id from Firebase console (Android)
    appId = "1:849900262025:android:28ecafcd6f792fa14cec58"

    // 🔍 Debug print to confirm resolved path
    println(
        "Resolved Firebase credentials path:" +
                " ${file("firebase-service-account.json").absolutePath}"
    )

    // service account json path (the workflow writes file to repo root)
    serviceCredentialsFile = file("firebase-service-account.json").absolutePath

    // who to notify. Either group(s) defined in Firebase console:
    groups = "family" // example group name(s), comma-separated if multiple

    // Or specify testers directly (comma-separated emails)
    // testers = "qa1@example.com,qa2@example.com"

    // optional release notes: you can pass via -PreleaseNotes in workflow
    // releaseNotes = "Automated CI release $VERSION_NAME"
}

tasks.register("prepareFirebaseCredentials") {
    doFirst {
        val credentials = System.getenv("FIREBASE_SERVICE_ACCOUNT_JSON")
        val file = File("${project.projectDir}/firebase-service-account.json")
        file.writeText(credentials ?: error("Missing FIREBASE_SERVICE_ACCOUNT_JSON"))
        println("✅ Firebase credentials written to ${file.absolutePath}")
    }
}

tasks.register("prepareReleaseKeystore") {
    doFirst {
        val base64Keystore = System.getenv("KEYSTORE_BASE64")
        val file = File("${project.projectDir}/release.jks")

        println("🔍 Preparing release.jks for signing...")
        println("🔍 Target path: ${file.absolutePath}")

        if (base64Keystore.isNullOrBlank()) {
            error("❌ KEYSTORE_BASE64 is missing or empty")
        }

        val decoded = Base64.getDecoder().decode(base64Keystore)
        file.writeBytes(decoded)

        println("✅ release.jks written successfully")
    }
}

fun DependencyHandler.betaImplementation(dependencyNotation: Any) {
    add("betaImplementation", dependencyNotation)
}

dependencies {

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.google.firebase.crashlytics)

    implementation(libs.firebase.feedback.api)
    betaImplementation(libs.karthik.pro.engr.firebase.feedback.impl)

}