version = "0.1.0"

dependencies {
    implementation(libs.ktx)
    implementation(libs.compat)
    implementation(libs.material)

    api(libs.bundles.navigation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.android.junit)
    androidTestImplementation(libs.android.espresso)
}