package arc.utilities.extension

/**
 * [NullabilityExtension] contains extension function to check null value of an Instance
 * @author jimlyas
 * @since 0.1.0
 *
 * Copyright © 2022-2024 jimlyas. All rights reserved.
 */
object NullabilityExtension {

	/**
	 * Extension function of [Any] to check does it is initialized or not?
	 * @return [Boolean] that define does [Any] is null or not?
	 * @receiver [Any]
	 */
	fun Any?.isNull() = this == null

	/**
	 * Extension function of [Any] to check does its' value is null or not?
	 * @return [Boolean] that define does [Any] is null?
	 * @receiver [Any]
	 */
	fun Any?.isNotNull() = this != null
}