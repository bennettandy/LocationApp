plugins {
    id("kotlin-kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = Modules.commonUiNamespace
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
        testInstrumentationRunner = Testing.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(project(Modules.common))
    implementation(project(Modules.locationDomainModule))

    implementation(platform(Compose.bom))
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(MaterialDesign.material)
    implementation(Compose.lifecycleRuntime)
    implementation(Compose.activityCompose)
    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.compiler)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.runtime)
    implementation(MaterialDesign.material3)
    implementation(Timber.timber)

    // Hilt
    implementation(DaggerHilt.hiltAndroid)
    implementation(AndroidX.coreKtx)
    kapt(DaggerHilt.hiltCompiler)

    testImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.espressoCore)

    // Roboelectric
    testImplementation(RoboElectric.roboElectric)
    testImplementation(Testing.composeUiTest)
    debugImplementation(Testing.uiTestManifest)
}
