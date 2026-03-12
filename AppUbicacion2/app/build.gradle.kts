plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    // CAMBIADO: Ahora coincide con tus carpetas y el import de tu MainActivity
    namespace = "com.example.miubicacionapp" // CAMBIAR A ESTE
    compileSdk = 35

    defaultConfig {
        // CAMBIADO: Para que Firebase reconozca tu App correctamente
        applicationId = "com.example.miubicacionapp" // CAMBIAR A ESTE
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity:1.9.3")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

    // LIBRERÍAS CLAVE
    implementation("com.mapbox.maps:android:11.0.0")
    implementation("com.google.firebase:firebase-database-ktx:20.3.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}