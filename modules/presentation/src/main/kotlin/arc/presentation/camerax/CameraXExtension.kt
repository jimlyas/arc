package arc.presentation.camerax

import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.CameraSelector.LensFacing
import androidx.camera.view.PreviewView
import arc.presentation.activity.ArcActivity
import arc.presentation.fragment.ArcFragment

/**
 * @author jimlyas
 * @since 03 Oct 2022
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */

/**
 * Function to configure [CameraXSetup] from given [ArcActivity] and also adding [androidx.camera.core.Preview]
 * @param view instance of [PreviewView] from the given [ArcActivity]
 * @param lensFacing Use the Lens Facing back or Front to use for the [Camera]
 * @receiver [ArcActivity]
 * @return instance of [CameraXSetup]
 */
fun ArcActivity<*>.setupCameraX(
    view: PreviewView,
    @LensFacing lensFacing: Int = LENS_FACING_BACK
) = CameraXSetup(view, this, lensFacing)

/**
 * Function to configure [CameraXSetup] from given [ArcFragment] and also adding [androidx.camera.core.Preview]
 * @param view instance of [PreviewView] from the given [ArcFragment]
 * @param lensFacing Use the Lens Facing back or Front to use for the [Camera]
 * @receiver [ArcFragment]
 * @return instance of [CameraXSetup]
 */
fun ArcFragment<*>.setupCameraX(
    view: PreviewView,
    @LensFacing lensFacing: Int = LENS_FACING_BACK
) = CameraXSetup(view, this, lensFacing)