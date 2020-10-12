package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.Product
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShowSavedManufacturers(
    private val productRepo: ProductRepo
) : FlowUseCase<Unit, List<String>>() {

    override fun execute(input: Unit): Flow<List<String>> {
        return productRepo.findAll().map { distinctManufacturers(it) }
    }

    private fun distinctManufacturers(list: List<Product>): List<String> {
        return list
            .map { it.manufacturer }
            .distinct()
            .filterNot { it.isEmpty() || it.isBlank() }
    }
}