package com.example.data.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.domain.CaresForBalance
import com.example.core.gateway.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal data class DataStoreAppPreferences(
    private val context: Context
) : AppPreferences {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")
    private val keyCaresForBalance = stringPreferencesKey("cares_for_balance")

    override fun getCaresForBalance(): Flow<CaresForBalance> {
        return context.dataStore.data
            .map { it[keyCaresForBalance] }
            .map { it?.let { CaresForBalance.valueOf(it) } ?: CaresForBalance.LAST_WEEK }
    }

    override suspend fun setCaresForBalance(value: CaresForBalance) {
        context.dataStore.edit {
            it[keyCaresForBalance] = value.toString()
        }
    }

}