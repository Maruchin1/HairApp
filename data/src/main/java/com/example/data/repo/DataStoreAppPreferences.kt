package com.example.data.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.domain.CaresLimit
import com.example.core.gateway.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal data class DataStoreAppPreferences(
    private val context: Context
) : AppPreferences {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

    private val keyPehBalanceCaresLimit = stringPreferencesKey("peh_balance_cares_limit")
    private val keyProductsRankingCaresLimit = stringPreferencesKey("products_ranking_cares_limit")

    override fun getPehBalanceCaresLimit(): Flow<CaresLimit> {
        return context.dataStore.data
            .map { it[keyPehBalanceCaresLimit] }
            .map { it?.let { CaresLimit.valueOf(it) } ?: CaresLimit.LAST_WEEK }
    }

    override fun getProductsRankingCaresLimit(): Flow<CaresLimit> {
        return context.dataStore.data
            .map { it[keyProductsRankingCaresLimit] }
            .map { it?.let { CaresLimit.valueOf(it) } ?: CaresLimit.LAST_WEEK }
    }

    override suspend fun setPehBalanceCaresLimit(value: CaresLimit) {
        context.dataStore.edit {
            it[keyPehBalanceCaresLimit] = value.toString()
        }
    }

    override suspend fun setProductsRankingCaresLimit(value: CaresLimit) {
        context.dataStore.edit {
            it[keyProductsRankingCaresLimit] = value.toString()
        }
    }

}