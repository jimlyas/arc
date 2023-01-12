package arc.presentation.fragment

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import arc.presentation.activity.ArcActivity
import arc.presentation.delegation.navigation.NavigationDelegate
import arc.presentation.delegation.navigation.NavigationDelegation
import arc.presentation.extension.inflateViewBinding

/**
 * @author jimlyas
 * @since 16 Sep 2022
 * @param viewBinding [ViewBinding] class to be implemented for this class
 * @property binding instance of [viewBinding] that have been inflated. Use [binding] for referencing your [android.view.View]
 * @property currentActivity instance of [ArcActivity] where the [ArcFragment] is attached to. If the [ArcFragment] is child Fragment, then [currentActivity] will be the [ArcActivity] of the [getParentFragment]
 *
 * Copyright © 2022 jimlyas. All rights reserved.
 */
abstract class ArcFragment<viewBinding : ViewBinding> : Fragment(),
    NavigationDelegate by NavigationDelegation() {

    protected val binding by lazy { inflateViewBinding() }

    protected lateinit var currentActivity: ArcActivity<*>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = binding.root

    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentActivity = when (context) {
            is ArcActivity<*> -> context
            else -> (parentFragment as ArcFragment<*>).currentActivity
        }
        currentActivity.navController?.let { this.setNavController(findNavController()) }
    }

    /**
     * Method to request permission to user
     * @param permissions list of permissions to request
     * @param onPermissionGranted action to do when permission granted
     * @param onPermissionNotGranted action to do when permission not granted
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(
        permissions: Array<String>,
        onPermissionGranted: (() -> Unit)? = null,
        onPermissionNotGranted: (() -> Unit)? = null
    ) {
        currentActivity.requestPermissionsSafely(
            permissions,
            onPermissionGranted,
            onPermissionNotGranted
        )
    }

    /**
     * Method to check if application has permission
     * @param permission name of the permission to check
     * @return is the application has the permission?
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermission(permission: String) =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                currentActivity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    /**
     * Method to check if the application has permissions
     * @param permissions array of the permission to check
     * @return is the application has the permissions?
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermissions(permissions: Array<String>): Boolean {
        var result = true
        permissions.forEach {
            if (!checkPermission(it)) {
                result = false
                return@forEach
            }
        }
        return result
    }

    /**
     * Function to finish the [ArcActivity] that current [ArcFragment] is attached to
     */
    fun finishActivity() {
        currentActivity.finish()
    }

    /**
     * Method to set [ArcActivity]'s [Toolbar] from [ArcFragment]
     * @param toolbar [Toolbar] that defined in XML layout, nullable
     * @param title Title for [Toolbar], nullable
     * @param isChild Display back button it toolbar?
     * @param menu menu Id, nullable if not needed
     * @param onMenuListener listener when item of the menu selected, nullable if not needed
     */
    fun setupToolbar(
        toolbar: Toolbar?,
        title: String?,
        isChild: Boolean,
        menu: Int?,
        onMenuListener: ((Int) -> Boolean)?
    ) {
        currentActivity.setupToolbar(toolbar, title, isChild, menu, onMenuListener)
    }
}