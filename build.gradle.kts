import org.gradle.internal.impldep.org.jsoup.safety.Safelist.basic

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath("com.android.tools.build:gradle:3.2.0")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url =  java.net.URI.create("https://api.mapbox.com/downloads/v2/releases/maven")
//            authentication {
//                basic(BasicAuthentication)
//            }
            credentials {
                // Do not change the username below.
                // This should always be `mapbox` (not your username).
                username = "mapbox"
                // Use the secret token you stored in gradle.properties as the password
                password = "sk.eyJ1IjoidWs4MzgwIiwiYSI6ImNsdTlmcXloejA4a3Qya3F1eDlyaTE0OW4ifQ.nKTNPfSoHDlx2jGqLNPCZQ"
            }
        }
    }
}