package com.example.hairapp

import android.app.Application
import com.example.common.commonModule
import com.example.core.coreModule
import com.example.data.dataModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(appModule, coreModule, dataModule, commonModule)
        }
    }
}