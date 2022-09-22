package arc.presentation.activity

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import arc.presentation.delegation.navigation.NavigationDelegate
import arc.presentation.delegation.navigation.NavigationDelegation
import arc.presentation.extension.inflateViewBinding

/**
 * @author jimlyas
 * @since 16 Sep 2022
 * @param viewBinding [ViewBinding] class to be implemented for this class
 * @param hostId Resource Id of [androidx.fragment.app.FragmentContainerView] inside the [Activity]
 * @property binding instance of [viewBinding] that have been inflated. Use [binding] for referencing your [android.view.View]
 * @property navController instance of [androidx.navigation.NavController] that has been embedded to the [androidx.fragment.app.FragmentContainerView]
 * @property permissionCallBack stored [Pair] of positive and negative action when permission is granted or not. Initially null, and can changed later on
 * @property permissionRequestContract [ActivityResultContracts] for requesting permission from the user
 * Copyright © 2022 jimlyas. All rights reserved.
 */
abstract class ArcActivity<viewBinding : ViewBinding>(@IdRes hostId: Int? = null) :
    AppCompatActivity(), NavigationDelegate by NavigationDelegation() {

    protected val binding by lazy { inflateViewBinding() }

    protected val navController by lazy { (hostId?.let { supportFragmentManager.findFragmentById(it) } as NavHostFragment).navController }

    private val permissionCallBack: Pair<(() -> Unit)?, (() -> Unit)?>? = null

    private val permissionRequestContract =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var granted = true
            it.forEach { entry -> if (!entry.value) granted = false }
            if (granted) permissionCallBack?.first?.invoke() else permissionCallBack?.second?.invoke()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setNavController(navController)
    }

    /**
     * Method to check if application has permission
     * @param permission name of the permission to check
     * @return Does the application has the requested permission?
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermission(permission: String) =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    /**
     * Method to check if the application has permissions
     * @param permissions array of the permission to check
     * @return Does the application has the requested permissions?
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
     * Method start another [ArcActivity] within the application
     * @param activity the [ArcActivity] to be started
     * @param isFinished After opening another [Activity], finish the previous [Activity]?
     * @param flags list of Flags used for opening new [Activity], for example [Intent.FLAG_ACTIVITY_NEW_TASK] or [Intent.FLAG_ACTIVITY_CLEAR_TOP]
     * @param args parameter or any data that will be used in the next [Activity]
     */
    inline fun <reified activity : ArcActivity<*>> start(
        isFinished: Boolean = false,
        flags: List<Int> = listOf(),
        args: MutableMap<String, java.io.Serializable> = mutableMapOf()
    ) {
        startActivity(Intent(this, activity::class.java).apply {
            flags.forEach(::addFlags)
            args.forEach { putExtra(it.key, it.value) }
        })
        if (isFinished) finish()
    }
}