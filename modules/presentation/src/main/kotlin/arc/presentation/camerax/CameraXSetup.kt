package arc.presentation.camerax

import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import androidx.camera.core.AspectRatio
import androidx.camera.core.AspectRatio.RATIO_16_9
import androidx.camera.core.AspectRatio.RATIO_4_3
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraX
import androidx.camera.core.FocusMeteringAction.Builder
import androidx.camera.core.FocusMeteringAction.FLAG_AF
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.core.UseCase
import androidx.camera.core.resolutionselector.AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import arc.presentation.camerax.CameraXSetup.Companion.RATIO_16_9
import arc.presentation.camerax.CameraXSetup.Companion.RATIO_4_3
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 * [CameraXSetup] define [CameraX] configuration and support manipulate its' functionality like adding and removing [UseCase]
 * @author jimlyas
 * @since 03 Oct 2022
 * @param lifeCycle the [LifecycleOwner] where the [androidx.camera.view.PreviewView] is being inflated
 * @param view instance of [PreviewView] from the given page
 * @param lensFacing Use the Lens Facing back or Front to use for the [Camera]
 * @property RATIO_16_9 used to defined when the current device ratio is 16 / 9
 * @property RATIO_4_3 used to defined when the current device ratio is 4 / 3
 * @property cameraProvider standard [ProcessCameraProvider] that binds all the [UseCase] to the [Camera]
 * @property cameraSelector defined which lens to use for the camera in the given [UseCase]
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
class CameraXSetup(
    private val view: PreviewView,
    private val lifeCycle: LifecycleOwner,
    @CameraSelector.LensFacing private val lensFacing: Int
) {

    companion object {
        const val RATIO_4_3 = 4.0 / 3.0
        const val RATIO_16_9 = 16.0 / 9.0
    }

    private val cameraProvider = ProcessCameraProvider.getInstance(view.context)
        .apply { addListener({}, ContextCompat.getMainExecutor(view.context)) }.get()
    private val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

    init {
        val camera = cameraProvider.bindToLifecycle(lifeCycle, cameraSelector,
            Preview.Builder()
                .setResolutionSelector(
                    ResolutionSelector.Builder()
                        .setAspectRatioStrategy(RATIO_16_9_FALLBACK_AUTO_STRATEGY).build()
                )
                .setTargetRotation(view.display.rotation)
                .build()
                .also { it.setSurfaceProvider(view.surfaceProvider) }
        )

        view.setOnTouchListener { _, motionEvent ->
            view.performClick()
            return@setOnTouchListener when (motionEvent.action) {
                ACTION_DOWN -> true
                ACTION_UP -> {
                    val factory = SurfaceOrientedMeteringPointFactory(
                        view.width.toFloat(),
                        view.height.toFloat()
                    )
                    val autoFocusPoint = factory.createPoint(motionEvent.x, motionEvent.y)
                    camera.cameraControl.startFocusAndMetering(
                        Builder(autoFocusPoint, FLAG_AF).apply { disableAutoCancel() }.build()
                    )
                    true
                }

                else -> false
            }
        }
    }

    /**
     * Function to add another [UseCase] to the list of [Camera]
     * @param useCase a new [UseCase] to add to the [Camera]
     */
    fun addUseCase(useCase: UseCase) {
        cameraProvider.bindToLifecycle(lifeCycle, cameraSelector, useCase)
    }

    /**
     * Function to remove [UseCase] to the list of [Camera]
     * @param useCase a new [UseCase] to add to the [Camera]
     */
    fun removeUseCase(useCase: UseCase) {
        cameraProvider.unbind(useCase)
    }

    /**
     * Function to calculate aspect ratio for preview camera
     * @param width width of the current device screen
     * @param height height of the current device screen
     * @return [AspectRatio] for previewing in current device
     */
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        return if (abs(previewRatio - RATIO_4_3) <= abs(previewRatio - RATIO_16_9))
            AspectRatio.RATIO_4_3 else AspectRatio.RATIO_16_9
    }
}