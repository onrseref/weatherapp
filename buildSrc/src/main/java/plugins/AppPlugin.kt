package plugins

import AppConfig
import Plugins
import com.android.build.gradle.AppExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.applyPlugins()
        target.configureAndroid()
    }

    private fun Project.applyPlugins() {
        plugins.apply(Plugins.Application)
        plugins.apply(Plugins.Kotlin.Android)
        plugins.apply(Plugins.Kotlin.Kapt)
        plugins.apply(Plugins.Kotlin.Parcelize)
        plugins.apply(Plugins.Hilt)
        plugins.apply(Plugins.Navigation)
    }

    private fun Project.configureAndroid() = this.extensions.getByType(AppExtension::class).run {
        compileSdkVersion(AppConfig.CompileSdk)

        defaultConfig.apply {
            minSdk = AppConfig.MinSdkVersion
            targetSdk = AppConfig.TargetSdkVersion
            versionCode = AppConfig.VersionCode
            versionName = AppConfig.VersionName
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions.apply {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        project.tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
                allWarningsAsErrors = false
            }
        }

        viewBinding.apply {
            isEnabled = true
        }

        signingConfigs {
            named("debug").configure {
                /* storeFile = project.rootProject.file("testkeystore.jks")
                 storePassword = "12121212"
                 keyPassword = "12121212"
                 keyAlias = "testkeystore"*/

                enableV2Signing = true
            }

            register("release") {
                /* storeFile = project.rootProject.file("testkeystore.jks")
                 storePassword = "12121212"
                 keyPassword = "12121212"
                 keyAlias = "testkeystore"*/

                enableV2Signing = true
            }
        }

        buildTypes.apply {
            getByName("release") {
                isDebuggable = false
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )

                signingConfig = signingConfigs.getByName("release")
            }

            getByName("debug") {
                isDebuggable = true
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )

                signingConfig = signingConfigs.getByName("debug")
            }
        }
        flavorDimensions("server")

        ndkVersion = "21.3.6528147"
    }
}