package com.example.corev2.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class InitializationCompletionStore @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("initialization_completion")
    private val databaseInitialized = booleanPreferencesKey("database_initialized")

    val databaseInitializedFlow: Flow<Boolean> = context.dataStore.data
        .map { it[databaseInitialized] ?: false }

    suspend fun setDatabaseInitialized(initialized: Boolean) {
        context.dataStore.edit {
            it[databaseInitialized] = initialized
        }
    }
}