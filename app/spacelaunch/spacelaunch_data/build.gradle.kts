plugins {
    id("kotlin-kapt")
    id("com.android.library")
    kotlin("android")
    id("com.apollographql.apollo3").version("3.7.5")
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

    implementation(project(Modules.common))

    // Hilt
    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")

    implementation("com.apollographql.apollo3:apollo-runtime")

    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

apollo {
    service("service") {
        srcDir("src/main/graphql")
        packageNamesFromFilePaths("com.example.rocketreserver")
    }
}
