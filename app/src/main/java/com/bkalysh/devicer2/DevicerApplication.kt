package com.bkalysh.devicer2

import android.app.Application
import com.bkalysh.devicer2.mocked.api.mockedApiAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DevicerApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DevicerApplication)
            modules(mockedApiAppModule)
            modules(appModule)
        }
    }
}