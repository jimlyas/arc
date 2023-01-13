version = "0.1.0"

dependencies {
    implementation(libs.ktx)
    implementation(libs.compat)
    implementation(libs.material)

    api(libs.bundles.navigation)

    api(libs.paging.runtime)

    api(libs.bundles.camerax)

    api("com.github.bumptech.glide:glide:4.13.2")

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
}