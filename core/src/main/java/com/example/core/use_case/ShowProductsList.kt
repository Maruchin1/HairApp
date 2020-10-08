package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.Product
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShowProductsList(
    private val productRepo: ProductRepo
) : FlowUseCase<Unit, List<Product>>() {

    override fun execute(input: Unit): Flow<List<Product>> {
        return productRepo.findAll().map { list ->
            list.sortedBy { it.name }
        }
    }
}