package arc.utilities.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * [DateExtension] contains extension function to manipulate [Date] formatting and parsing
 * @author jimlyas
 * @since 0.1.0
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
object DateExtension {

	/**
	 * Extension function of [String] to parse [String] as a [Date]
	 * @param fromFormat pattern of the [String] to parse
	 * @param locale type to locale to parse the [String]
	 * @return parsed [Date] from the given [String]
	 * @receiver [String]
	 */
	fun String.toDate(fromFormat: String, locale: Locale = Locale.getDefault()) =
		SimpleDateFormat(fromFormat, locale).parse(this)

	/**
	 * Extension function of [DateExtension] to format [Date] to a [String]
	 * @param toFormat pattern of the [String] to format
	 * @param locale type of locale to format the [Date]
	 * @return parsed [String] from the given [Date]
	 * @receiver [Date]
	 */
	fun Date.toString(toFormat: String, locale: Locale = Locale.getDefault()) =
		SimpleDateFormat(toFormat, locale).format(this)

	/**
	 * Extension function of [Long] to parse unix timestamp as [String] in the form of [Date]
	 * @param toFormat pattern of the [String] to format
	 * @param locale type of locale to format the [Date]
	 * @return parsed [String] from the given [Date]
	 * @receiver [Long]
	 */
	fun Long.toDate(toFormat: String, locale: Locale = Locale.getDefault()) =
		SimpleDateFormat(toFormat, locale).format(Date(this))
}