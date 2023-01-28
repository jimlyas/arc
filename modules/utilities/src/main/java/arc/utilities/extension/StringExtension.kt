package arc.utilities.extension

import java.text.NumberFormat
import java.util.*

/**
 * [StringExtension] contains extension function to parse and format [String] to other form
 * @author jimlyas
 * @since 16 Jan 2023
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
object StringExtension {

    /**
     * Function to convert [String] to formatted [String] as currency
     * @param locale [Locale] to define which Currency to use to format the [String]
     * @param useDot used to replace , with . in the String
     * @return formatted [String] as currency
     * @receiver [String]
     */
    fun String.toCurrency(locale: Locale = Locale.getDefault(), useDot: Boolean = false): String {
        val formatter = NumberFormat.getCurrencyInstance(locale).apply {
            maximumFractionDigits = 0
        }

        return if (useDot) formatter.format(this).replace(",", ".")
        else formatter.format(this)
    }
}
