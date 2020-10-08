package com.example.hairapp

import android.app.Application
import com.example.core.coreModule
import com.example.data.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(appModule, coreModule, dataModule)
        }
    }
}