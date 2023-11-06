package arc.utilities.security

import android.content.Context
import androidx.annotation.IntDef
import arc.utilities.extension.ContextExtension.isDebuggable
import arc.utilities.security.DeviceChecker.EMULATOR
import arc.utilities.security.DeviceChecker.ROOTED
import arc.utilities.security.DeviceChecker.SAFE
import com.framgia.android.emulator.EmulatorDetector
import com.scottyab.rootbeer.RootBeer
import kotlin.annotation.AnnotationRetention.SOURCE

/**
 * [DeviceChecker] [Object] to validate tampered device
 * @author jimlyas
 * @since 0.1.0
 * @property EMULATOR indicate that the device is an emulator
 * @property ROOTED indicate that the device is rooted
 * @property SAFE indicate that the device is safe
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
object DeviceChecker {
	const val EMULATOR = 0
	const val ROOTED = 2
	const val SAFE = 1

	@IntDef(EMULATOR, ROOTED, SAFE)
	@Retention(SOURCE)
	annotation class SecurityCheck

	/**
	 * Function to check does the device is an emulator
	 * @param ctx [Context] to check current device condition and configuration
	 * @return [Boolean] Does the device is an emulator or not?
	 */
	fun isDeviceEmulator(ctx: Context): Boolean {
		var result = true
		EmulatorDetector.with(ctx).apply {
			isCheckTelephony = true
			addPackageName("com.bluestacks")
			isDebug = ctx.isDebuggable
			detect { result = it }
		}
		return result
	}

	/**
	 * Function to check does the device is rooted
	 * @param ctx [Context] to check current device condition and configuration
	 * @return [Boolean] Does the device is rooted or not?
	 */
	fun isDeviceRooted(ctx: Context) = RootBeer(ctx).isRooted
}