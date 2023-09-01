plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

gradlePlugin {
    plugins {
        register("AppPlugin") {
            id = "AppPlugin"
            implementationClass = "plugins.AppPlugin"
        }
        register("AndroidLibraryPlugin") {
            id = "AndroidLibraryPlugin"
            implementationClass = "plugins.AndroidLibraryPlugin"
        }
        register("KotlinLibraryPlugin") {
            id = "KotlinLibraryPlugin"
            implementationClass = "plugins.KotlinLibraryPlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())
    implementation("com.android.tools.build:gradle:7.1.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.41")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
}
