package arc.presentation.delegation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

/**
 * [NavigationDelegation] control how the Navigation will be implemented using delegation
 * @author jimlyas
 * @since 18 Sep 2022
 * @property nav [NavController] that will being used to navigate through the Fragments
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
class NavigationDelegation : NavigationDelegate {

    private lateinit var nav: NavController

    override fun setNavController(controller: NavController) {
        nav = controller
    }

    override fun navigateTo(navId: Int, args: Bundle?, options: NavOptions?) {
        nav.navigate(navId, args, options)
    }

    override fun navigateTo(link: Uri, options: NavOptions?) {
        nav.navigate(link, options, null)
    }
}