package arc.utilities.security

import androidx.annotation.StringDef
import kotlin.annotation.AnnotationRetention.SOURCE

/**
 * [Installer] annotation class to define all verified app installer
 * @author jimlyas
 * @since 0.1.0
 *
 * Copyright © 2022-2024 jimlyas. All rights reserved.
 */
@StringDef(GOOGLE_PLAY_STORE, AMAZON_APP_STORE, HUAWEI_APP_GALLERY)
@Retention(SOURCE)
annotation class Installer


const val GOOGLE_PLAY_STORE = "com.android.vending"
const val AMAZON_APP_STORE = "com.amazon.venezia"
const val HUAWEI_APP_GALLERY = "com.huawei.appmarket"