plugins {
    id("kotlin-kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = Modules.locationDomainModuleNamespace
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
        testInstrumentationRunner = Testing.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = Build.sourceCompatibility
        targetCompatibility = Build.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Build.kotlinJvmTarget
    }
}

dependencies {

    implementation(project(Modules.locationDataModule))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Google.material)

    // Hilt
    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    // Testing
    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.espressoCore)
}
