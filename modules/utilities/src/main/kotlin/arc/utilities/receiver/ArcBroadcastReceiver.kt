package arc.utilities.receiver

import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_START
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

	abstract fun exported(): Boolean

	override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
		if (event == ON_START) registerReceiver(
			ctx, this, broadcastIntent(),
			if (exported()) RECEIVER_EXPORTED else RECEIVER_NOT_EXPORTED
		) else if (event == ON_DESTROY) ctx.unregisterReceiver(this)
	}
}