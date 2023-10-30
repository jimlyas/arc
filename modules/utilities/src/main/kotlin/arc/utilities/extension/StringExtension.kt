package arc.utilities.extension

import android.util.Base64.NO_WRAP
import android.util.Base64.encodeToString
import android.webkit.MimeTypeMap
import java.io.File
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

    /**
     * Function to get the MimeType of the given path
     * @return [String] of file extension from given path
     * @receiver [String]
     */
    fun String.getMimeType(): String? {
        return MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(
                MimeTypeMap.getFileExtensionFromUrl(
                    this.substring(this.lastIndexOf("."))
                )
            )
    }

    /**
     * Function to convert [String] of file path into [Base64] [String]
     * @return [String] of base [Base64] from given file
     * @receiver [String]
     */
    fun String.imageToBase64() =
        "data:" + this.getMimeType() + ";base64," +
                encodeToString(File(this).readBytes(), NO_WRAP)
}
