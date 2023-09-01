import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    object Hilt {
        const val Android = "com.google.dagger:hilt-android:${Versions.Hilt}"
        const val AndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Hilt}"
    }

    //Retrofit
    object Retrofit {
        const val Self = "com.squareup.retrofit2:retrofit:${Versions.Retrofit}"
        const val ConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.Retrofit}"
    }

    object Glide {
        private const val Version = "4.13.1"
        const val Self = "com.github.bumptech.glide:glide:${Version}"
        const val Compiler = "com.github.bumptech.glide:compiler:${Version}"
    }

    //Okhttp
    const val Okhttp = "com.squareup.okhttp3:okhttp:${Versions.Okhttp}"
    const val OkhttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.Okhttp}"


    const val CoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.CoroutinesAndroid}"

    const val Gson = "com.google.code.gson:gson:2.9.0"

    const val CoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.CoroutinesAndroid}"

    const val FragmentKtx = "androidx.fragment:fragment-ktx:${Versions.FragmentKtx}"

    const val Material = "com.google.android.material:material:${Versions.Material}"

    const val MPAndroidChart= "com.github.PhilJay:MPAndroidChart:${Versions.MPAndroidChart}"

    object Navigation {
        const val NavigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.Navigation}"
        const val NavigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.Navigation}"
        const val NavigationFeature =
            "androidx.navigation:navigation-dynamic-features-fragment:${Versions.Navigation}"
    }

    object Androidx {
        const val Recyclerview =
            "androidx.recyclerview:recyclerview:${Versions.Androidx.Recyclerview}"
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.Androidx.AppCompat}"

        const val CoreKtx = "androidx.core:core-ktx:${Versions.CoreKtxVersion}"
        const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Kotlin}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Androidx.ConstraintLayout}"

        const val Paging = "androidx.paging:paging-runtime-ktx:${Versions.Androidx.Paging}"

        const val ViewPager2 = "androidx.viewpager2:viewpager2:${Versions.Androidx.ViewPager2}"

        object Lifecycle {
            private const val LifecycleVersion = "2.4.0"
            const val Extensions =
                "androidx.lifecycle:lifecycle-extensions:${LifecycleVersion}"
            const val LiveDataKtx =
                "androidx.lifecycle:lifecycle-livedata-ktx:$LifecycleVersion}"
            const val ViewModelKtx =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${LifecycleVersion}"
            const val RuntimeKtx =
                "androidx.lifecycle:lifecycle-runtime-ktx:${LifecycleVersion}"
        }
    }

    object TestDependencies {
        const val Junit = "junit:junit:${Versions.Junit}"
        const val Mockito = "org.mockito:mockito-core:${Versions.Mockito}"
        const val Ext = "androidx.test.ext:junit:${Versions.Ext}"
        const val Core = "androidx.test:core:${Versions.Test}"
        const val Runner = "androidx.test:runner:${Versions.Test}"
        const val Rules = "androidx.test:rules:${Versions.Test}"
        const val ArchCore = "androidx.arch.core:core-testing:${Versions.ArchCore}"
        const val Truth = "com.google.truth:truth:${Versions.Truth}"
    }
}

fun DependencyHandler.lifecycle() {
    implementation(Dependencies.Androidx.Lifecycle.Extensions)
    implementation(Dependencies.Androidx.Lifecycle.LiveDataKtx)
    implementation(Dependencies.Androidx.Lifecycle.ViewModelKtx)
}

private fun DependencyHandler.compileOnly(depName: String) {
    add("compileOnly", depName)
}

private fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}

private fun DependencyHandler.api(depName: String) {
    add("api", depName)
}
