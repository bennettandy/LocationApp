plugins {
    id("kotlin-kapt")
    id("com.android.library")
    kotlin("android")
    id("com.apollographql.apollo3").version(Apollo.apolloVersion)
}

android {
    namespace = Modules.spacelaunchDataModuleNamespace
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

    // Hilt
    implementation(DaggerHilt.hiltAndroid)
    implementation(AndroidX.coreKtx)
    kapt(DaggerHilt.hiltCompiler)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(MaterialDesign.material)

    implementation(Apollo.apolloRuntime)

    implementation(Navigation.navigationCompose)
    implementation(AndroidX.securityCrypto)

    testImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.espressoCore)
}

apollo {
    service("service") {
        srcDir("src/main/graphql")
        packageNamesFromFilePaths(Modules.spacelaunchDataModuleNamespace)
    }
}
