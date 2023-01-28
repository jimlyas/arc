package arc.utilities.context

import android.annotation.SuppressLint
import android.content.Context
import arc.utilities.context.GlobalContext.context

/**
 *
 * Static object as Singleton to contain current application's [Context]
 * @author jimlyas
 * @since 25 Jan 2023
 * @property context [Context] of current application
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
@SuppressLint("StaticFieldLeak")
object GlobalContext {
    private var context: Context? = null

    /**
     * Function to initialize [Context] to use across application
     * @param mContext Current application's [Context]
     */
    private fun init(mContext: Context) {
        context = mContext
    }

    /**
     * Function to get current application's [Context]
     * @return Application [Context]
     */
    private fun getContext() = context ?: throw IllegalStateException("call init first")

    /**
     * Function to call inside Application class to register current [Context]
     * @param mContext Current application's [Context]
     */
    @JvmStatic
    fun setBaseContext(mContext: Context) {
        init(mContext)
    }

    /**
     * Function to get current application's [Context]
     * @return [Context] of current application
     */
    @JvmStatic
    fun get() = getContext()
}