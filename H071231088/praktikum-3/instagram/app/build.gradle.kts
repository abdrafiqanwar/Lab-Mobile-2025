plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.instagram"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.instagram"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("androidx.recyclerview:recyclerview:1.2.1")// or latest version
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.google.android.material:material:1.6.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.google.android.material:material:1.12.0")


}