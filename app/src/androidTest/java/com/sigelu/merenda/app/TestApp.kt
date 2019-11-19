package com.sigelu.merenda.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        /**
         * Not initialising any modules.
         * Tests will decide which module definitions to load, override or mock
         */
        startKoin{
            androidContext(this@TestApp)
            modules(listOf())
        }
    }
}
