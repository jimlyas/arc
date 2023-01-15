package arc.presentation.exception

/**
 * [NoSupportedTypeThrowable] defines error which the given type is not supported for the operation
 * @author jimlyas
 * @since 15 Jan 2023
 * @param message note indicating the throwable that occurred
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
class NoSupportedTypeThrowable(override val message: String?) : Throwable()