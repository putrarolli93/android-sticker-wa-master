package com.icaali.ga

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco

internal class StikerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this);
        appContext = applicationContext
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}