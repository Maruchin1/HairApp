package com.example.hairapp

import android.app.Application
import com.example.common.commonModule
import com.example.core.coreModule
import com.example.corev2.database.DatabaseInitializer
import com.example.data.dataModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var databaseInitializer: DatabaseInitializer

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(appModule, coreModule, dataModule, commonModule)
        }

        databaseInitializer.checkIfInitialized()
    }
}