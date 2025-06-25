// File: StarbucksMembershipApp/app/build.gradle.kts (LEVEL MODUL)

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.starbucksmembershipapp"
    compileSdk = 34 // Menggunakan 34 lebih stabil daripada 35 (preview)

    defaultConfig {
        applicationId = "com.example.starbucksmembershipapp"
        minSdk = 24
        targetSdk = 34 // Menggunakan 34 lebih stabil
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Kembali ke 1.8 lebih umum untuk XML
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    // Menonaktifkan Compose dan mengaktifkan ViewBinding untuk XML
    buildFeatures {
        compose = false // PENTING: Dinonaktifkan
        viewBinding = true // PENTING: Diaktifkan untuk mempermudah akses ke View XML
    }
}

dependencies {
    // Implementasi dependensi dari libs.versions.toml
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.material)
    implementation(libs.androidx.constraintlayout)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

    // Navigation (untuk XML Fragments)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}