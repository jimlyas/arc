package arc.utilities.extension

import android.content.Context
import android.content.pm.ApplicationInfo
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.COLOR_SCHEME_SYSTEM
import androidx.browser.customtabs.CustomTabsIntent.SHARE_STATE_OFF
import arc.utilities.R

/**
 * Function to open an in-app browser
 *
 * @author jimlyas
 * @since 13 Jan 2023
 * @param url link to open using browser
 * @receiver [Context]
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
fun Context.openWeb(url: String) {
    CustomTabsIntent.Builder().apply {
        setShowTitle(true)
        setUrlBarHidingEnabled(true)
        setColorScheme(COLOR_SCHEME_SYSTEM)
        setInstantAppsEnabled(false)
        setStartAnimations(this@openWeb, R.anim.right_in, R.anim.left_out)
        setExitAnimations(this@openWeb, R.anim.left_in, R.anim.right_out)
        setShareState(SHARE_STATE_OFF)
    }.build().launchUrl(this, Uri.parse(url))
}

/**
 * Variable to check does the current application is debuggable or not
 */
val Context.isDebuggable: Boolean
    get() = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0