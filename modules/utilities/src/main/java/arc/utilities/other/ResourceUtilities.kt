package arc.utilities.other

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import arc.utilities.context.GlobalContext

/**
 * [ResourceUtilities] is utilities class for returning Resources like [String] and [Drawable]
 * @author jimlyas
 * @since 25 Jan 2023
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
object ResourceUtilities {

    /**
     * Function to get a [String] resource
     * @param id Id resource of the [String]
     * @return [String] from the given Id
     */
    fun getString(@StringRes id: Int): String = GlobalContext.get().getString(id)

    /**
     * Function to get an [Array] [String] resource
     * @param id Id resource of the [Array] [String]
     * @return [Array] of [String] from the given Id
     */
    fun getStringArray(id: Int): Array<String> =
        GlobalContext.get().resources.getStringArray(id)

    /**
     * Function to get [Int] value of color
     * @param id Id resource of the color
     * @return [Int] value of the color from the given Id
     */
    fun getColor(@ColorRes id: Int): Int =
        ResourcesCompat.getColor(GlobalContext.get().resources, id, null)

    /**
     * Function to get [Drawable] resource
     * @param id Id resource of the [Drawable]
     * @return [Drawable] resource from the given Id
     */
    fun getDrawable(@DrawableRes id: Int): Drawable? =
        ResourcesCompat.getDrawable(GlobalContext.get().resources, id, null)
}