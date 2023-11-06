version = "0.1.0"

dependencies {
    implementation(libs.ktx)
    implementation(libs.compat)

    api(libs.bundles.room)
    api("net.zetetic:android-database-sqlcipher:4.5.3")
    api("androidx.sqlite:sqlite:2.3.0")

    api(libs.paging.runtime)

    api(libs.bundles.retrofit)

    api(libs.okhttp3)

    api(libs.android.security)

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
}