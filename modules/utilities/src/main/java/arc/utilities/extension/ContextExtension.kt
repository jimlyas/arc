package arc.utilities.extension

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.ApplicationInfo
import android.media.RingtoneManager
import android.net.Uri
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.COLOR_SCHEME_SYSTEM
import androidx.browser.customtabs.CustomTabsIntent.SHARE_STATE_OFF
import androidx.core.app.NotificationCompat
import arc.utilities.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 *
 * @author jimlyas
 * @since 13 Jan 2023
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
object ContextExtension {

    /**
     * Extension function of [Context] to open an in-app browser
     * @param url link to open using browser
     * @receiver [Context]
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

    /**
     * Extension function of [Context] to display
     * @param message the [String] to display inside the [Toast]
     * @param isItLong Does the [Toast] displayed for a long time?
     * @receiver [Context]
     */
    fun Context.showToast(message: String, isItLong: Boolean = false) {
        Toast.makeText(
            this, message,
            if (isItLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Extension function of [Context] to display Material Dialog
     * @param title [String] as the title of the dialog
     * @param message [String] as the message of the dialog
     * @param positiveAction set of [Pair] to define the positive title button and its' action
     * @param negativeAction set of [Pair] to define the negative title button and its' action, nullable by default
     */
    fun Context.showDialog(
        title: String,
        message: String,
        positiveAction: Pair<String, (() -> Unit)?>,
        negativeAction: Pair<String, (() -> Unit)?>? = null
    ) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveAction.first) { dialog, _ ->
                positiveAction.second?.invoke()
                dialog.dismiss()
            }
            negativeAction?.let { action ->
                setNegativeButton(action.first) { dialog, _ ->
                    action.second?.invoke()
                    dialog.dismiss()
                }
            }
        }.show()
    }

    /**
     * Extension function of [Context] to display notification
     * @param channelId [String] of group notification that this notification will be displayed in
     * @param title [String] title of the notification
     * @param message [String] message of the notification
     * @param icon [DrawableRes] as the notification icon
     * @param notificationId id for current notification
     * @param pendingIntent [PendingIntent] to run when notification is clicked, nullable by default
     * @receiver [Context]
     */
    fun Context.showNotification(
        channelId: String,
        title: String,
        message: String,
        @DrawableRes icon: Int,
        notificationId: Int = 0,
        pendingIntent: PendingIntent? = null
    ) {
        ((this).getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
            notificationId,
            NotificationCompat.Builder(this, channelId).apply {
                setSmallIcon(icon)
                setContentTitle(title)
                setContentText(message)
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                setContentIntent(pendingIntent)
                setStyle(NotificationCompat.BigTextStyle().bigText(message))
            }.build()
        )
    }
}