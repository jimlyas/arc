package arc.presentation.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import arc.presentation.activity.ArcActivity
import arc.presentation.fragment.ArcFragment
import java.lang.reflect.ParameterizedType

/**
 * @author jimlyas
 * @since 16 Sep 2022
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */

/**
 * Function to inflate a [ViewBinding] from [ArcActivity]
 *
 * @param viewBinding type of [ViewBinding] to inflate
 * @return instance of [viewBinding] that have been inflated
 * @receiver [ArcActivity]
 */
internal fun <viewBinding : ViewBinding> ArcActivity<viewBinding>.inflateViewBinding() =
    findViewBindingClass().inflateViewBinding<viewBinding>(layoutInflater)

/**
 * Function to inflate a [ViewBinding] from [ArcFragment]
 *
 * @param viewBinding type of [ViewBinding] to inflate
 * @return instance of [viewBinding] that have been inflated
 * @receiver [ArcActivity]
 */
internal fun <viewBinding : ViewBinding> ArcFragment<viewBinding>.inflateViewBinding() =
    findViewBindingClass().inflateViewBinding<viewBinding>(layoutInflater)

/**
 * Function to check does the given class is a [ViewBinding] or not
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
 * Function to check does the [Class] have inflate method (that all [ViewBinding] have)
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
 * Function to run inflate method from any [Class] to get the [ViewBinding] instance
 *
 * @return instance of [ViewBinding] that have been inflated
 * @receiver [Class]
 */
@Suppress("UNCHECKED_CAST")
internal fun <V : ViewBinding> Class<*>.inflateViewBinding(layoutInflater: LayoutInflater) =
    getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as V

/**
 * Function to run inflate method from given [ViewBinding] using java reflection
 *
 * @return instance of [ViewBinding] that have been inflated
 * @receiver [ViewGroup]
 */
inline fun <reified V : ViewBinding> ViewGroup.inflateViewBinding() =
    V::class.java.getMethod(
        "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
    ).invoke(null, LayoutInflater.from(context), this, false) as V


/**
 * Function to run inflate method from given [ViewBinding] using java reflection
 *
 * @return instance of [ViewBinding] that have been inflated
 * @receiver [LayoutInflater]
 */
inline fun <reified V : ViewBinding> LayoutInflater.inflateViewBinding() =
    V::class.java.getMethod(
        "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
    ).invoke(null, this, null, false) as V