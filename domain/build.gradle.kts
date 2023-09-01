import modules.Modules
import plugins.AndroidLibraryPlugin

apply<AndroidLibraryPlugin>()
apply(plugin = Plugins.Hilt)

dependencies {
    implementation(project(Modules.Data))
    implementation(Dependencies.CoroutinesAndroid)
    implementation(Dependencies.CoroutinesCore)
    hilt()
    okhttp()
    retrofit()
}