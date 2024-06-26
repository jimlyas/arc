package arc.presentation.activity

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager.LayoutParams.FLAG_SECURE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import arc.presentation.delegation.navigation.NavigationDelegate
import arc.presentation.delegation.navigation.NavigationDelegation
import arc.presentation.extension.ViewBindingExtension.inflateViewBinding
import java.io.Serializable

/**
 * [ArcActivity] define base class for [Activity] for project
 *
 * @author jimlyas
 * 0.1.0 16 Sep 2022
 * @param viewBinding [ViewBinding] class to be implemented for this class
 * @param hostId Resource Id of [androidx.fragment.app.FragmentContainerView] inside the [Activity]
 * @property binding instance of [viewBinding] that have been inflated. Use [binding] for referencing your [android.view.View]
 * @property menuId the Id of the menu that are attached to the [Toolbar], null by default
 * @property menuListener the listener for the item menu, null by default
 * @property navController instance of [androidx.navigation.NavController] that has been embedded to the [androidx.fragment.app.FragmentContainerView]
 * @property permissionCallBack stored [Pair] of positive and negative action when permission is granted or not. Initially null, and can changed later on
 * @property permissionRequestContract [ActivityResultContracts] for requesting permission from the user
 * @property selectedImageListener listener to invoke when user pick an image
 * @property pickImageContract [ActivityResultContracts] for picking image from user
 * Copyright © 2022-2024 jimlyas. All rights reserved.
 */
abstract class ArcActivity<viewBinding : ViewBinding>(@IdRes hostId: Int? = null) :
	AppCompatActivity(), NavigationDelegate by NavigationDelegation() {

	protected val binding by lazy { inflateViewBinding() }

	private var menuId: Int? = null
	private var menuListener: ((Int) -> Boolean)? = null

	val navController: NavController? by lazy {
		(hostId?.let {
			supportFragmentManager.findFragmentById(it)
		} as NavHostFragment).navController
	}

	private var permissionCallBack: Pair<(() -> Unit)?, (() -> Unit)?>? = null
	private val permissionRequestContract =
		registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
			var granted = true
			it.forEach { entry -> if (!entry.value) granted = false }
			permissionCallBack?.let { callBack ->
				if (granted) callBack.first?.invoke() else callBack.second?.invoke()
			}
		}

	private var selectedImageListener: ((Uri) -> Unit)? = null
	private var pickImageContract =
		registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
			uri?.let { selectedImageListener?.invoke(it) }
		}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		navController?.let(::setNavController)
	}

	/**
	 * Function to pick image from gallery
	 * @param action action to do when an image is selected
	 */
	fun pickImageFromGallery(action: (Uri) -> Unit) {
		selectedImageListener = action
		pickImageContract.launch("image/*")
	}

	/**
	 * Function to check if application has permission
	 * @param permission name of the permission to check
	 * @return Does the application has the requested permission?
	 */
	@TargetApi(M)
	fun checkPermission(permission: String) =
		SDK_INT < M || checkSelfPermission(permission) == PERMISSION_GRANTED

	/**
	 * Function to check if the application has permissions
	 * @param permissions array of the permission to check
	 * @return Does the application has the requested permissions?
	 */
	@TargetApi(M)
	fun checkPermissions(permissions: List<String>): Boolean {
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
	 * Function to check permission to the user
	 * @param permissions [List] of [String] permission to ask the user
	 * @param onPermissionGranted action to run when all the permissions is granted
	 * @param onPermissionNotGranted action to run when all or one of the permissions is not granted
	 */
	fun checkPermissions(
		permissions: List<String>,
		onPermissionGranted: (() -> Unit),
		onPermissionNotGranted: (() -> Unit)
	) {
		val result = checkPermissions(permissions)

		if (result) onPermissionGranted.invoke()
		else onPermissionNotGranted.invoke()
	}

	/**
	 * Function to request permission to user
	 * @param permissions list of permissions to request
	 * @param onPermissionGranted action to do when permission granted
	 * @param onPermissionNotGranted action to do when permission not granted
	 */
	@TargetApi(M)
	fun requestPermissionsSafely(
		permissions: Array<String>,
		onPermissionGranted: (() -> Unit)? = null,
		onPermissionNotGranted: (() -> Unit)? = null
	) {
		val notGrantedPermission = arrayListOf<String>().apply {
			permissions.forEach {
				if (!checkPermission(it)) this.add(it)
			}
		}
		permissionCallBack = Pair(onPermissionGranted, onPermissionNotGranted)
		permissionRequestContract.launch(notGrantedPermission.toTypedArray())
	}

	/**
	 * Function to prevent screenshot and screen recording any page from within the application
	 */
	protected fun secureScreen() {
		window.setFlags(FLAG_SECURE, FLAG_SECURE)
	}

	/**
	 * Function to clear screenshot and screen recording prevention
	 */
	protected fun clearSecureScreen() {
		window.clearFlags(FLAG_SECURE)
	}

	/**
	 * Function to set [ArcActivity]'s [Toolbar]
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
		menuId = menu
		menuListener = onMenuListener
		toolbar?.let {
			setSupportActionBar(it)
			supportActionBar?.let { tb ->
				title?.let { title -> tb.title = title }
				tb.setDisplayHomeAsUpEnabled(isChild)
				invalidateOptionsMenu()
			}
		} ?: run {
			supportActionBar?.let {
				it.title = title
				it.setDisplayHomeAsUpEnabled(isChild)
				invalidateOptionsMenu()
			}
		}
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuId?.let { menuInflater.inflate(it, menu) }
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		menuListener?.invoke(item.itemId)
		return super.onOptionsItemSelected(item)
	}

	/**
	 * Function start another [ArcActivity] within the application
	 * @param activity the [ArcActivity] to be started
	 * @param isFinished After opening another [Activity], finish the previous [Activity]?
	 * @param flags list of Flags used for opening new [Activity], for example [Intent.FLAG_ACTIVITY_NEW_TASK] or [Intent.FLAG_ACTIVITY_CLEAR_TOP]
	 * @param args parameter or any data that will be used in the next [Activity]
	 */
	inline fun <reified activity : ArcActivity<*>> start(
		isFinished: Boolean = false,
		flags: List<Int> = listOf(),
		args: MutableMap<String, Serializable> = mutableMapOf()
	) {
		startActivity(Intent(this, activity::class.java).apply {
			flags.forEach(::addFlags)
			args.forEach { putExtra(it.key, it.value) }
		})
		if (isFinished) finish()
	}
}