package arc.utilities.other

import timber.log.Timber

/**
 * [LoggingUtilities] [Timber] logging implementation
 * @author jimlyas
 * @since 0.1.0
 *
 * Copyright © 2022-2024 jimlyas. All rights reserved.
 */
object LoggingUtilities {

	/**
	 * Function to check does [Timber] have tree planted
	 * @param block action of logging using [Timber]
	 */
	private fun log(block: () -> Unit) {
		if (Timber.treeCount > 0) block()
	}

	/**
	 * Function to send verbose log message
	 * @param message [String] of message you would like logged
	 * @param t a [Throwable] to log, nullable by default
	 */
	fun logVerbose(message: String, t: Throwable? = null) =
		log { Timber.v(t, message) }

	/**
	 * Function to send debug log message
	 * @param message [String] of message you would like logged
	 * @param t a [Throwable] to log, nullable by default
	 */
	fun logDebug(message: String, t: Throwable? = null) =
		log { Timber.d(t, message) }

	/**
	 * Function to send info log message
	 * @param message [String] of message you would like logged
	 * @param t a [Throwable] to log, nullable by default
	 */
	fun logInfo(message: String, t: Throwable? = null) =
		log { Timber.i(t, message) }

	/**
	 * Function to send warn log message
	 * @param message [String] of message you would like logged
	 * @param t a [Throwable] to log, nullable by default
	 */
	fun logWarn(message: String, t: Throwable? = null) =
		log { Timber.w(t, message) }

	/**
	 * Function to send error log message
	 * @param message [String] of message you would like logged
	 * @param t a [Throwable] to log, nullable by default
	 */
	fun logError(message: String, t: Throwable? = null) =
		log { Timber.e(t, message) }

	/**
	 * Function to send wtf log message
	 * @param message [String] of message you would like logged
	 * @param t a [Throwable] to log, nullable by default
	 */
	fun logWtf(message: String, t: Throwable? = null) =
		log { Timber.wtf(t, message) }
}