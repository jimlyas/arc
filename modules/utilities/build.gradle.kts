version = "0.1.0"

dependencies {
    implementation(libs.ktx)
    implementation(libs.compat)
    implementation(libs.material)
    implementation(libs.runtime)
    implementation(libs.compat)

    api("androidx.browser:browser:1.4.0")
    api("id.zelory:compressor:3.0.1")
    api("com.jakewharton.timber:timber:5.0.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
}