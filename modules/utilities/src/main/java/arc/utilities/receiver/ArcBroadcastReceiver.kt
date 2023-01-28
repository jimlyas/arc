package arc.utilities.receiver

import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @author jimlyas
 * @since 16 Jan 2023
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
abstract class ArcBroadcastReceiver : BroadcastReceiver(), LifecycleEventObserver {

    abstract fun broadcastIntent(): IntentFilter

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START) {

        } else if (event == Lifecycle.Event.ON_DESTROY) {

        }
    }
}