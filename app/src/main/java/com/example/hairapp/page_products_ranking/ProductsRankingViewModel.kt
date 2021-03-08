package com.example.hairapp.page_products_ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.Care
import com.example.core.domain.CaresLimit
import com.example.core.domain.Product
import com.example.core.gateway.AppPreferences
import com.example.core.gateway.CareRepo
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

class ProductsRankingViewModel(
    private val careRepo: CareRepo,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val allCares: Flow<List<Care>> = careRepo.findAll()
    private val _caresLimit: Flow<CaresLimit> = appPreferences.getProductsRankingCaresLimit()

    val caresLimit: LiveData<CaresLimit> = _caresLimit.asLiveData()

    val productsRanking: LiveData<List<Product>> = _caresLimit
        .flatMapLatest { loadCaresWithLimit(it) }
        .mapLatest { getProductsFromCares(it) }
        .mapLatest { makeRanking(it) }
        .asLiveData()


    private fun loadCaresWithLimit(caresLimit: CaresLimit): Flow<List<Care>> {
        return when (caresLimit) {
            CaresLimit.ALL -> allCares
            else -> careRepo.findLastN(caresLimit.daysLimit)
        }
    }

    private fun getProductsFromCares(cares: List<Care>): List<Product> {
        return cares.flatMap { it.getStepsProducts() }
    }

    private fun makeRanking(products: List<Product>): List<Product> {
        return products
            .groupingBy { it.id }
            .eachCount()
            .let { makeRankingByCounts(products, it) }
    }

    private fun makeRankingByCounts(products: List<Product>, counts: Map<Int, Int>): List<Product> {
        return products
            .distinctBy { it.id }
            .sortedByDescending { counts[it.id] }
    }
}