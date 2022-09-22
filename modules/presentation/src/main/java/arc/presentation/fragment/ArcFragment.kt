package arc.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import arc.presentation.delegation.navigation.NavigationDelegate
import arc.presentation.delegation.navigation.NavigationDelegation
import arc.presentation.extension.inflateViewBinding

/**
 * @author jimlyas
 * @since 16 Sep 2022
 * @param viewBinding [ViewBinding] class to be implemented for this class
 * @property binding instance of [viewBinding] that have been inflated. Use [binding] for referencing your [android.view.View]
 *
 * Copyright © 2022 jimlyas. All rights reserved.
 */
abstract class ArcFragment<viewBinding : ViewBinding> : Fragment(),
    NavigationDelegate by NavigationDelegation() {

    protected val binding by lazy { inflateViewBinding() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavController(findNavController())
    }
}