import com.google.firebase.appdistribution.gradle.firebaseAppDistribution
import java.util.Base64

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.firebase.appdistribution)
}

android {
    namespace = "com.karthik.pro.engr.algocompose"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.karthik.pro.engr.algocompose"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    }
}

// ---- Firebase App Distribution block (module-level) ----
firebaseAppDistribution {
    // required: your app id from Firebase console (Android)
    appId = "1:849900262025:android:28ecafcd6f792fa14cec58"

    // 🔍 Debug print to confirm resolved path
    println("Resolved Firebase credentials path:" +
            " ${file("firebase-service-account.json").absolutePath}")

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

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // Add these for ViewModel with Compose:
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.all.variants.preview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}