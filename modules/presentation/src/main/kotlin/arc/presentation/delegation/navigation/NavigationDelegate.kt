package arc.presentation.delegation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions

/**
 * [NavigationDelegate] define functions surrounding the Jetpack [androidx.navigation.Navigation] implementation
 * @author jimlyas
 * @since 18 Sep 2022
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
interface NavigationDelegate {

    /**
     * Function to set a [NavController] to the component
     * @param controller the [NavController] to be set
     */
    fun setNavController(controller: NavController)

    /**
     * Function to navigate to another [androidx.fragment.app.Fragment] using Jetpack [androidx.navigation.Navigation]
     * @param navId resource Id for action that link to navigate the [androidx.fragment.app.Fragment]
     * @param args arguments or any data to pass that will be used in destination [androidx.fragment.app.Fragment], by default null
     * @param options used to store special options for navigation actions for example animation and single top, by default null
     */
    fun navigateTo(@IdRes navId: Int, args: Bundle? = null, options: NavOptions? = null)

    /**
     * Function to navigate to another [androidx.fragment.app.Fragment] using Deep Link [Uri] with Jetpack [androidx.navigation.Navigation]
     * @param link the deeplink [Uri] for opening [androidx.fragment.app.Fragment]
     * @param options used to store special options for navigation actions for example animation and single top, by default null
     */
    fun navigateTo(link: Uri, options: NavOptions? = null)
}