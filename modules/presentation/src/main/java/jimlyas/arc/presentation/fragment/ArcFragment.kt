package jimlyas.arc.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import jimlyas.arc.presentation.extension.inflateViewBinding

/**
 * @author jimlyas
 * @since 16/09/22
 *
 * Copyright © 2022 jimlyas. All rights reserved.
 */
class ArcFragment<viewBinding : ViewBinding> : Fragment() {

    protected val binding by lazy { inflateViewBinding() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}