package com.harmittaa.publictransitstops.application

import android.app.Application
import com.harmittaa.publictransitstops.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class PublicTransitStopsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PublicTransitStopsApplication)
            modules(appModule)
        }
    }
}
