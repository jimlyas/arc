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

    private var errorHandler: ((Exception) -> Unit)? = null

    override fun setNavController(controller: NavController) {
        nav = controller
    }

    override fun navigateTo(navId: Int, args: Bundle?, options: NavOptions?) {
        runNavigationSafely { nav.navigate(navId, args, options) }
    }

    override fun navigateTo(link: Uri, options: NavOptions?) {
        runNavigationSafely { nav.navigate(link, options, null) }
    }

    /**
     * Function to set [errorHandler] value
     * @param action how the [Exception] will be handled when occurred
     */
    fun setNavigationException(action: (Exception) -> Unit) {
        errorHandler = action
    }

    /**
     * Function to run navigation action safely without throwing un-catch exception, set the
     * error handler by calling [NavigationDelegation.setNavigationException]
     * @param navigationAction navigation action that will be run
     * @exception [IllegalStateException]
     * @exception [IllegalArgumentException]
     * @see [NavigationDelegation.setNavigationException]
     */
    private fun runNavigationSafely(navigationAction: () -> Unit) {
        try {
            navigationAction()
        } catch (stateException: IllegalStateException) {
            errorHandler?.invoke(stateException)
        } catch (argumentException: IllegalArgumentException) {
            errorHandler?.invoke(argumentException)
        }
    }
}