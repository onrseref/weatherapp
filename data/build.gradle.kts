
import plugins.AndroidLibraryPlugin

apply<AndroidLibraryPlugin>()
apply(plugin = Plugins.Hilt)

dependencies {
    implementation(Dependencies.CoroutinesAndroid)
    implementation(Dependencies.CoroutinesCore)
    room()
    hilt()
    okhttp()
    retrofit()
}