package arc.utilities.other

import android.graphics.Bitmap
import arc.utilities.context.GlobalContext
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.size
import java.io.File

/**
 * [FileUtilities] File manipulation and configuration utilities
 * @author jimlyas
 * @since 0.1.0
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
object FileUtilities {

	/**
	 * Function to compress [File] image size
	 * @param toCompress [File] to compress
	 * @param format [Bitmap.CompressFormat] configuration for the compression
	 * @return compressed [File]
	 */
	suspend fun compressImage(toCompress: File, format: Bitmap.CompressFormat? = null) =
		Compressor.compress(GlobalContext.get(), toCompress) {
			format(format ?: Bitmap.CompressFormat.JPEG)
			size(1024)
		}
}