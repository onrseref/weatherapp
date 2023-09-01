import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler


fun DependencyHandler.room() {
    kapt(Dependencies.Androidx.Room.ROOM_COMPILER)
    implementation(Dependencies.Androidx.Room.ROOM_RUNTIME)
    implementation(Dependencies.Androidx.Room.ROOM_KTX)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.Hilt.Android)
    kapt(Dependencies.Hilt.AndroidCompiler)
}

fun DependencyHandler.okhttp() {
    implementation(Dependencies.Okhttp)
    implementation(Dependencies.OkhttpLoggingInterceptor)
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.Retrofit.Self)
    implementation(Dependencies.Retrofit.ConverterGson)
}

fun DependencyHandler.glide() {
    implementation(Dependencies.Glide.Self)
    kapt(Dependencies.Glide.Compiler)
}

fun DependencyHandler.navigation() {
    implementation(Dependencies.Navigation.NavigationFragment)
    implementation(Dependencies.Navigation.NavigationUi)
    implementation(Dependencies.Navigation.NavigationFeature)
}

fun DependencyHandler.unitTest() {
    testImplementation(Dependencies.TestDependencies.Junit)
    testImplementation(Dependencies.TestDependencies.Mockito)
    testImplementation(Dependencies.TestDependencies.Ext)
    testImplementation(Dependencies.TestDependencies.Core)
    testImplementation(Dependencies.TestDependencies.Runner)
    testImplementation(Dependencies.TestDependencies.Rules)
    testImplementation(Dependencies.TestDependencies.ArchCore)
    testImplementation(Dependencies.TestDependencies.Truth)
}

fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

fun DependencyHandler.testImplementation(depName: String) {
    add("testImplementation", depName)
}

fun DependencyHandler.debugImplementation(dependencyNotation: Any): Dependency? =
    add("debugImplementation", dependencyNotation)

fun DependencyHandler.releaseImplementation(dependencyNotation: Any): Dependency? =
    add("releaseImplementation", dependencyNotation)

fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.annotationProcessor(dependencyNotation: Any): Dependency? =
    add("annotationProcessor", dependencyNotation)

private fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}