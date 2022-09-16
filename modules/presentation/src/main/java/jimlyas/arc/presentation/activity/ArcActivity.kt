package jimlyas.arc.presentation.activity

import android.app.Activity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import jimlyas.arc.presentation.extension.inflateViewBinding

/**
 * @author jimlyas
 * @since 16/09/22
 * @param viewBinding [ViewBinding] class to be implemented for this class
 *
 * Copyright © 2022 jimlyas. All rights reserved.
 */
abstract class ArcActivity<viewBinding : ViewBinding> : Activity() {

    protected val binding by lazy { inflateViewBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}