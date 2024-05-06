plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")


}

android {
    namespace = "com.arnavigation.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.arnavigation.app"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("com.google.android.libraries.places:places:3.4.0")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    implementation("androidx.camera:camera-view:1.3.2")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-firestore:24.11.0")
    val nav_version = "2.7.3"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("androidx.navigation:navigation-compose:2.7.7")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation ("com.google.android.gms:play-services-maps:18.2.0") // Add Google Maps dependency
    implementation ("com.google.maps.android:maps-ktx:3.2.0") // Additional Maps KTX dependency for easier usage with Kotlin
    implementation ("com.google.maps:google-maps-services:0.18.0") // Google Maps Services dependency
    implementation ("com.google.maps.android:android-maps-utils:2.2.3") // Android Maps Utils dependency
    implementation ("com.google.maps.android:maps-utils-ktx:3.2.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.11.0")
    implementation("androidx.preference:preference-ktx:1.2.1") // Additional Maps Utils KTX dependency for easier usage with Kotlin

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.camera:camera-core:1.1.0")
    implementation("androidx.camera:camera-camera2:1.1.0")
    implementation("androidx.camera:camera-lifecycle:1.1.0")
    implementation("androidx.camera:camera-view:1.0.0-alpha30")
    implementation("androidx.compose.ui:ui:1.1.0")
    implementation("androidx.compose.foundation:foundation:1.1.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("com.google.accompanist:accompanist-permissions:0.20.0")

    //slipt
    implementation("com.google.android.libraries.places:places:3.4.0")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    implementation("androidx.camera:camera-view:1.3.2")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.camera:camera-core:1.1.0")
    implementation("androidx.camera:camera-camera2:1.1.0")
    implementation("androidx.camera:camera-lifecycle:1.1.0")
    implementation("androidx.camera:camera-view:1.0.0-alpha30")
    implementation("androidx.compose.ui:ui:1.1.0")
    implementation("androidx.compose.foundation:foundation:1.1.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("com.google.accompanist:accompanist-permissions:0.20.0")
    implementation ("com.google.android.libraries.places:places:2.4.0")
    implementation ("com.google.android.libraries.places:places:3.1.0")
//    implementation ("com.github.florent37:runtime-permission-kotlin:2.0.0")
    implementation ("org.osmdroid:osmdroid-android:6.1.11")
//    implementation("com.google.maps.android.compose:2.7.0")
    implementation("com.google.maps.android:maps-compose:2.7.2")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("com.google.ar:core:1.33.0")
//    implementation("io.github.sceneview:arsceneview:2.0.4")

    implementation ("com.google.ar.sceneform.ux:sceneform-ux:1.17.1")
    implementation ("com.google.ar.sceneform:core:1.17.1")
    implementation ("com.google.ar.sceneform:assets:1.17.1")


    
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
   kapt("androidx.hilt:hilt-compiler:1.0.0")




}
