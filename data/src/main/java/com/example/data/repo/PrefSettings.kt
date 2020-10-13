package com.example.data.repo

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.example.core.domain.CareStep
import com.example.core.gateway.Settings
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrefSettings(
    private val context: Context
) : Settings {
    private val dataStore: DataStore<Preferences> by lazy {
        context.createDataStore(name = "settings")
    }
    private val serializer by lazy { Gson() }

    private val keyCareSchema = preferencesSetKey<String>("care-schema")

    override suspend fun setCareSchema(schema: List<CareStep>) {
        dataStore.edit { prefs ->
            val serializedSet = schema
                .map { serializer.toJson(it) }
                .toSet()
            prefs[keyCareSchema] = serializedSet
        }
    }

    override fun getCareSchema(): Flow<List<CareStep>> {
        return dataStore.data.map { prefs ->
            val serializedSet = prefs[keyCareSchema] ?: emptySet()
            serializedSet.map { serializer.fromJson(it, CareStep::class.java) }
        }
    }

}