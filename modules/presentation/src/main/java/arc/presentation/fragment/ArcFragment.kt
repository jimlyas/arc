package arc.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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
}