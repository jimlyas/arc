version = "0.1.0"

dependencies {
    implementation(libs.ktx)
    implementation(libs.compat)

    api(libs.bundles.room)

    api(libs.paging.runtime)

    api(libs.bundles.retrofit)

    api(libs.okhttp3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
}