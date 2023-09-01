import modules.Modules
import plugins.AndroidLibraryPlugin

apply<AndroidLibraryPlugin>()
apply(plugin = Plugins.Hilt)

dependencies {

    implementation(Dependencies.Androidx.AppCompat)
    implementation(Dependencies.Androidx.ConstraintLayout)
    implementation(Dependencies.Material)
    implementation(Dependencies.Androidx.Lifecycle.ViewModelKtx)
    implementation(Dependencies.Androidx.Lifecycle.RuntimeKtx)
    implementation(Dependencies.Androidx.CoreKtx)
    implementation(Dependencies.FragmentKtx)
    implementation(project(Modules.Base))
    implementation(project(Modules.Domain))
    implementation(project(Modules.Data))
    implementation(project(Modules.Detail))
    hilt()
    glide()
    navigation()
}
