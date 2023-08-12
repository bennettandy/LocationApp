plugins {
    id("kotlin-kapt")
    id("com.android.library")
    kotlin("android")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = Modules.authPresentationModuleNamespace
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
                "proguard-rules.pro",
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
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

    implementation(project(Modules.authDomainModule))
    implementation(project(Modules.common))
    implementation(project(Modules.commonUi))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(MaterialDesign.material)
    implementation(Navigation.navigationCompose)

    // Firebase
    implementation(Firebase.authentication)

    // Hilt
    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    // Room
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)
    kapt(Room.roomCompiler)

    // Testing
    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.espressoCore)

    implementation(MaterialDesign.material3)
    implementation(Compose.lifecycleRuntime)
    implementation(Compose.activityCompose)
    implementation(platform(Compose.bom))
    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiToolingPreview)
    implementation(Timber.timber)
}
