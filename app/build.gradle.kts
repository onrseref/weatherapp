import modules.Modules

apply<plugins.AppPlugin>()

plugins {
    id(Plugins.Application)
    id(Plugins.Kotlin.Android)
    id(Plugins.Kotlin.Kapt)
    id(Plugins.Kotlin.Parcelize)
    id(Plugins.Hilt)
    id(Plugins.Navigation)
}

android {
    defaultConfig {
        applicationId = AppConfig.ApplicationId
    }
}

dependencies {
    implementation(project(Modules.Base))
    implementation(project(Modules.Data))
    implementation(project(Modules.Domain))
    implementation(project(Modules.Listing))
    implementation(Dependencies.Androidx.AppCompat)
    implementation(Dependencies.Androidx.ConstraintLayout)
    implementation(Dependencies.Material)
    implementation(Dependencies.Androidx.Lifecycle.ViewModelKtx)
    implementation(Dependencies.Androidx.Lifecycle.RuntimeKtx)
    implementation(Dependencies.Androidx.CoreKtx)
    hilt()
    glide()
    navigation()
}
