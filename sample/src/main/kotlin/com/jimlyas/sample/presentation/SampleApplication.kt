package com.jimlyas.sample.presentation

import android.app.Application
import android.content.Context
import arc.utilities.context.GlobalContext.setBaseContext

class SampleApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        base?.let { setBaseContext(base) }
    }
}