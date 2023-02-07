package arc.utilities.receiver

import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import arc.utilities.context.GlobalContext

/**
 * [ArcBroadcastReceiver] abstract [BroadcastReceiver] functionality
 * @author jimlyas
 * @since 16 Jan 2023
 * @property ctx [android.content.Context] to register and unregister current [ArcBroadcastReceiver]
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
abstract class ArcBroadcastReceiver : BroadcastReceiver(), LifecycleEventObserver {

    protected val ctx = GlobalContext.get()

    abstract fun broadcastIntent(): IntentFilter

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START) ctx.registerReceiver(this, broadcastIntent())
        else if (event == Lifecycle.Event.ON_DESTROY) ctx.unregisterReceiver(this)
    }
}