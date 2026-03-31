plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.socialmedia1903"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.socialmedia1903"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.foundation.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("io.coil-kt:coil-compose:2.7.0")

    val roomVersion = "2.6.1"

    // Room Runtime and KTX (Kotlin Extensions/Coroutines)
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // Use KSP instead of KAPT for the compiler
    ksp("androidx.room:room-compiler:$roomVersion")

    // Optional: Paging 3 support
    // implementation("androidx.room:room-paging:$roomVersion")

    // Optional: Room Testing
    testImplementation("androidx.room:room-testing:$roomVersion")

    val hiltVersion = "2.52"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-android-compiler:$hiltVersion") // KSP Processor

    // Hilt Navigation Compose (Required for hiltViewModel() in Compose)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // --- RETROFIT ---
    val retrofitVersion = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // OkHttp & Logging (Essential for SDK 35 network security)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    val pagingVersion = "3.3.5"

    // Core Paging Library
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

    // Jetpack Compose Integration (Essential for your UI)
    implementation("androidx.paging:paging-compose:$pagingVersion")

    // Room Integration (If you are doing Database + Network paging)
    implementation("androidx.room:room-paging:2.6.1")

    // Testing Paging
    testImplementation("androidx.paging:paging-common-ktx:$pagingVersion")

    implementation("io.coil-kt:coil-compose:2.7.0")

    implementation("com.google.accompanist:accompanist-swiperefresh:0.32.0")
    implementation("io.socket:socket.io-client:2.1.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0")
}