package arc.presentation.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import arc.presentation.extension.ViewBindingExtension.inflateViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * [ArcBottomSheet] Base class for BottomSheet. Use [BottomSheetDialogFragment]
 * @author jimlyas
 * @since 0.1.0
 * @param viewBinding [ViewBinding] define which layout to inflate for this [ArcBottomSheet]
 * @param isDialogCancelable Does the [ArcBottomSheet] cancelable of not?
 * @property binding instance of [viewBinding] that can be use to manipulate the [android.view.View] components
 *
 * Copyright © 2022-2024 jimlyas. All rights reserved.
 */
abstract class ArcBottomSheet<viewBinding : ViewBinding>(isDialogCancelable: Boolean) :
	BottomSheetDialogFragment() {

	init {
		isCancelable = isDialogCancelable
	}

	protected val binding by lazy { inflateViewBinding() }

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	) = binding.root
}