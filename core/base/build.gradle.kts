import plugins.AndroidLibraryPlugin

apply<AndroidLibraryPlugin>()

dependencies {
    implementation(Dependencies.Androidx.AppCompat)
    implementation(Dependencies.Material)
    implementation(Dependencies.Androidx.Kotlin)
    implementation(Dependencies.Androidx.CoreKtx)
    room()
    glide()
}