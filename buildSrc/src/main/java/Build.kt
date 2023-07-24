import org.gradle.api.JavaVersion

object Build {
    private const val androidBuildToolsVersion = "7.0.4"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    private const val hiltAndroidGradlePluginVersion = "2.38.1"
    const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltAndroidGradlePluginVersion"

    private const val ktlintpluginVersion = "11.3.1"
    const val ktLintPlugin = "org.jlleitschuh.gradle:ktlint-gradle:$ktlintpluginVersion"

    private const val coreLibraryDesugaring = "2.0.3"
    const val coreLibraryDesugar = "com.android.tools:desugar_jdk_libs:$coreLibraryDesugaring"

    const val kotlinJvmTarget = "17"

    @JvmField // so Groovy code doesn't complain of Access Rights
    public val sourceCompatibility = JavaVersion.VERSION_17
    @JvmField
    public val targetCompatibility = JavaVersion.VERSION_17
}