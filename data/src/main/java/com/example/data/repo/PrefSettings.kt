package com.example.data.repo

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.example.core.gateway.Settings

class PrefSettings(
    private val context: Context
) : Settings {
    private val dataStore: DataStore<Preferences> by lazy {
        context.createDataStore(name = "settings")
    }

}