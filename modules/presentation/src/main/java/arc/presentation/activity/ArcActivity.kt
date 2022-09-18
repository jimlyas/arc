package arc.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import arc.presentation.extension.inflateViewBinding

/**
 * @author jimlyas
 * @since 16/09/22
 * @param viewBinding [ViewBinding] class to be implemented for this class
 * @param hostId Resource Id of [androidx.fragment.app.FragmentContainerView] inside the [Activity]
 *
 * Copyright © 2022 jimlyas. All rights reserved.
 */
abstract class ArcActivity<viewBinding : ViewBinding>(@IdRes hostId: Int? = null) : Activity() {

    protected val binding by lazy { inflateViewBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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
        args: MutableMap<String, java.io.Serializable>? = null
    ) {
        startActivity(Intent(this, activity::class.java).apply {
            flags.forEach(::addFlags)
            args?.forEach { putExtra(it.key, it.value) }
        })
        if (isFinished) finish()
    }
}