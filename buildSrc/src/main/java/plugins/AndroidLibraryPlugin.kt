package plugins

import AppConfig
import Plugins
import com.android.build.api.dsl.BuildType
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.applyPlugins()
        target.configureAndroid()
    }

    private fun Project.applyPlugins() {
        plugins.apply(Plugins.Library)
        plugins.apply(Plugins.Kotlin.Android)
        plugins.apply(Plugins.Kotlin.Kapt)
        plugins.apply(Plugins.Kotlin.Parcelize)
        plugins.apply(Plugins.Navigation)
    }

    private fun Project.configureAndroid() =
        this.extensions.getByType(LibraryExtension::class).run {
            compileSdk = AppConfig.CompileSdk

            defaultConfig.apply {
                minSdk = AppConfig.MinSdkVersion
                targetSdk = AppConfig.TargetSdkVersion
                vectorDrawables.useSupportLibrary = true
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

            viewBinding.isEnabled = true

            buildTypes.apply {
                getByName("release") {
                    isDebuggable = false
                    isMinifyEnabled = true
                    proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
                    buildConfigStringField("BASE_URL", "https://api.open-meteo.com")
                    buildConfigStringField("SESSION_TIMEOUT", "120")
                    buildConfigStringField(
                        "DAILY",
                        "weathercode,temperature_2m_max,temperature_2m_min"
                    )
                    buildConfigStringField("TIMEZONE", "auto")
                    buildConfigStringField("LATITUDE", "57.7009921")
                    buildConfigStringField("LONGITUDE", "11.8938365")
                }

                getByName("debug") {
                    isDebuggable = true
                    isMinifyEnabled = false
                    proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
                    buildConfigStringField("BASE_URL", "https://api.open-meteo.com")
                    buildConfigStringField("SESSION_TIMEOUT", "120")
                    buildConfigStringField(
                        "DAILY",
                        "weathercode,temperature_2m_max,temperature_2m_min"
                    )
                    buildConfigStringField("TIMEZONE", "auto")
                    buildConfigStringField("LATITUDE", "57.7009921")
                    buildConfigStringField("LONGITUDE", "11.8938365")
                }
            }
        }

    private fun BuildType.buildConfigStringField(name: String, value: String) {
        this.buildConfigField("String", name, "\"$value\"")
    }
}