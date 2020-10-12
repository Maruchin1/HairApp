package com.example.data.repo

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.core.gateway.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrefSettings(
    private val context: Context
) : Settings {
    private val dataStore: DataStore<Preferences> by lazy {
        context.createDataStore(name = "settings")
    }
    private val MANUFACTURERS = preferencesKey<MutableList<String>>("manufacturers")

    override suspend fun addManufacturer(manufacturer: String) {
        dataStore.edit {
            it[MANUFACTURERS]?.let { currList ->
                currList.add(manufacturer)
                it[MANUFACTURERS] = currList
            }
        }
    }

    override fun findSavedManufacturers(): Flow<List<String>> {
        return dataStore.data.map { it[MANUFACTURERS] ?: emptyList() }
    }

}