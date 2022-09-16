package jimlyas.arc.presentation.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import jimlyas.arc.presentation.activity.ArcActivity
import jimlyas.arc.presentation.fragment.ArcFragment
import java.lang.reflect.ParameterizedType

/**
 * @author jimlyas
 * @since 16/09/22
 *
 * Copyright © 2022 jimlyas. All rights reserved.
 */

/**
 * Method to inflate a [ViewBinding] from [ArcActivity]
 *
 * @param viewBinding type of [ViewBinding] to inflate
 * @return instance of [viewBinding] that have been inflated
 * @receiver [ArcActivity]
 */
internal fun <viewBinding : ViewBinding> ArcActivity<viewBinding>.inflateViewBinding() =
    findViewBindingClass().inflateViewBinding<viewBinding>(layoutInflater)

/**
 * Method to inflate a [ViewBinding] from [ArcFragment]
 *
 * @param viewBinding type of [ViewBinding] to inflate
 * @return instance of [viewBinding] that have been inflated
 * @receiver [ArcActivity]
 */
internal fun <viewBinding : ViewBinding> ArcFragment<viewBinding>.inflateViewBinding() =
    findViewBindingClass().inflateViewBinding<viewBinding>(layoutInflater)

/**
 * Method to check does the given class is a [ViewBinding] or not
 *
 * @return desired [Class] to found, in this case is the [ViewBinding]
 * @receiver [Any]
 */
internal fun Any.findViewBindingClass(): Class<*> {
    var javaClass: Class<*>? = this.javaClass
    var result: Class<*>? = null
    while (result == null || !result.checkInflateMethod()) {
        result =
            (javaClass?.genericSuperclass as? ParameterizedType)?.actualTypeArguments?.firstOrNull {
                if (it is Class<*>) it.checkInflateMethod() else false
            } as? Class<*>
        javaClass = javaClass?.superclass
    }
    return result
}

/**
 * Method to check does the [Class] have inflate method (that all [ViewBinding] have)
 * @return [Boolean] Does the [Class] have inflate method or not?
 * @receiver [Class]
 */
internal fun Class<*>.checkInflateMethod() = try {
    getMethod("inflate", LayoutInflater::class.java)
    true
} catch (ex: Exception) {
    false
}


/**
 * Method to run inflate method from any [Class] to get the [ViewBinding] instance
 *
 * @return instance of [ViewBinding] that have been inflated
 * @receiver [Class]
 */
internal fun <V : ViewBinding> Class<*>.inflateViewBinding(layoutInflater: LayoutInflater) =
    try {
        @Suppress("UNCHECKED_CAST")
        getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as V
    } catch (t: Throwable) {
        throw t
    }

/**
 * Method to run inflate method from given [ViewBinding] using java reflection
 *
 * @return instance of [ViewBinding] that have been inflated
 * @receiver [ViewGroup]
 */
inline fun <reified V : ViewBinding> ViewGroup.inflateViewBinding() =
    V::class.java.getMethod(
        "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
    ).invoke(null, LayoutInflater.from(context), this, false) as V


/**
 * Method to run inflate method from given [ViewBinding] using java reflection
 *
 * @return instance of [ViewBinding] that have been inflated
 * @receiver [LayoutInflater]
 */
inline fun <reified V : ViewBinding> LayoutInflater.inflateViewBinding() =
    V::class.java.getMethod(
        "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
    ).invoke(null, this, null, false) as V