package arc.presentation.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import arc.presentation.exception.NoSupportedTypeThrowable
import arc.presentation.extension.ImageViewLoadConfiguration.ImageTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * [ImageViewLoadConfiguration] define setup and configuration to load an image into [ImageView] using [Glide]
 *
 * @author jimlyas
 * @since 13 Jan 2023
 * @param source the image source that will be loaded into [ImageView], supported type is [Int], [String], and [Drawable]
 * @param transformation [ImageTransformation] to define how the size of the image will be displayed by the ratio of [ImageView]
 * @param progressBar [ProgressBar] that will be listen when the image is loaded, will be displayed
 * when the image still currently loading and will be hidden when the image already loaded, null by default
 * @param onImageLoaded listener for when the image is successfully loaded, null by default
 * @param onImageNotLoaded listener for when the image is failed to load, null by default
 * @property options [RequestOptions] for holding [Glide] configuration
 * @property loadListener [RequestListener] to listen the state of image loading, used for
 * displaying and hiding [ProgressBar] and running [onImageLoaded] or [onImageNotLoaded]
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
class ImageViewLoadConfiguration(
    var source: Any? = null,
    var transformation: ImageTransformation? = null,
    var progressBar: ProgressBar? = null,
    var onImageLoaded: (() -> Unit)? = null,
    var onImageNotLoaded: (() -> Unit)? = null,
) {
    val options = RequestOptions()
    val loadListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            progressBar?.gone()
            onImageNotLoaded?.invoke()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            progressBar?.gone()
            onImageLoaded?.invoke()
            return false
        }
    }

    /**
     * Function to set the image source to load to [ImageView]
     * @param source the image source that will be loaded into [ImageView], supported type is [Int], [String], and [Drawable]
     * @throws Throwable when the [source] is not supported type
     */
    fun withImageSource(source: Any?) {
        when (source) {
            is Drawable -> this.source = source
            is Int -> this.source = source
            is String -> this.source = source
            else -> throw NoSupportedTypeThrowable("data type not supported for image source")
        }
    }

    /**
     * Function to register placeHolder image while the image is still loading
     * @param placeHolder the image to show, supported type is [Int] and [Drawable]
     * @throws Throwable when the [source] is not supported type
     */
    fun withPlaceHolder(placeHolder: Any?) {
        when (placeHolder) {
            is Drawable -> options.placeholder(placeHolder)
            is Int -> options.placeholder(placeHolder)
            else -> throw NoSupportedTypeThrowable("only Drawable and resource Id is supported for place holder")
        }
    }

    /**
     * Function to register error image when failed to load the source image
     * @param errorImage the image to show, supported type is [Int] and [Drawable]
     * @throws Throwable when the [source] is not supported type
     */
    fun withErrorImage(errorImage: Any?) {
        when (errorImage) {
            is Drawable -> options.error(errorImage)
            is Int -> options.error(errorImage)
            else -> throw NoSupportedTypeThrowable("only Drawable and resource Id is supported for error image")
        }
    }

    /**
     * Function to set transformation to the image being loaded, e.g [ImageTransformation.FitCenter] or [ImageTransformation.Circle]
     * @param transformation [ImageTransformation] to transform the source image
     */
    fun withTransformation(transformation: ImageTransformation) {
        when (transformation) {
            ImageTransformation.CenterInside -> options.centerInside()
            ImageTransformation.CenterCrop -> options.centerCrop()
            ImageTransformation.FitCenter -> options.fitCenter()
            ImageTransformation.Circle -> options.circleCrop()
        }
    }

    /**
     * Function to register [ProgressBar] to listen to loading source image
     * @param progressBar [ProgressBar] that will be listen when the image is loaded, will be displayed
     * when the image still currently loading and will be hidden when the image already loaded
     */
    fun withProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
    }

    /**
     * Function to register listener when the source image is successfully loaded
     * @param action listener for when the image is successfully loaded
     */
    fun withImageLoaded(action: () -> Unit) {
        onImageLoaded = action
    }

    /**
     * Function to register listener when the source image is failed to load
     * @param action listener for when the image is failed to load
     */
    fun withImageNotLoaded(action: () -> Unit) {
        onImageNotLoaded = action
    }

    /**
     * enum class that define image transformation for [Glide]
     */
    enum class ImageTransformation {
        CenterCrop, FitCenter, CenterInside, Circle
    }
}

typealias loadConfiguration = ImageViewLoadConfiguration.() -> Unit

/**
 * Function to load image into [ImageView]
 * @param configuration [loadConfiguration] define the [Glide] configuration need to load the image
 * @receiver [ImageView]
 */
fun ImageView.loadImage(configuration: loadConfiguration) {
    val config = ImageViewLoadConfiguration().apply(configuration)
    Glide.with(context)
        .load(config.source)
        .apply(config.options)
        .listener(config.loadListener)
        .into(this)
}

