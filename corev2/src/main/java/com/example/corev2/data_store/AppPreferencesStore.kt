package com.example.corev2.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.corev2.entities.CaresLimit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AppPreferencesStore(
    private val context: Context
) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("app_preferences")
    private val databaseInitialized = booleanPreferencesKey("database_initialized")
    private val pehBalanceCaresLimit = stringPreferencesKey("peh_balance_cares_limit")

    val databaseInitializedFlow: Flow<Boolean> = context.datastore.data
        .map { it[databaseInitialized] ?: false }

    val pehBalanceCaresLimitFlow: Flow<CaresLimit> = context.datastore.data
        .map { it[pehBalanceCaresLimit] }
        .map { it?.let { CaresLimit.valueOf(it) } ?: CaresLimit.ALL }

    suspend fun setDatabaseInitialized(initialized: Boolean) {
        context.datastore.edit {
            it[databaseInitialized] = initialized
        }
    }

    suspend fun setPehBalanceCaresLimit(caresLimit: CaresLimit) {
        context.datastore.edit {
            it[pehBalanceCaresLimit] = caresLimit.toString()
        }
    }

    suspend fun clear() {
        context.datastore.edit {
            it.clear()
        }
    }
}