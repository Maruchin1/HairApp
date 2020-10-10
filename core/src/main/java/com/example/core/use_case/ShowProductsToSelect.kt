package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.Product
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.flow.Flow

class ShowProductsToSelect(
    private val productRepo: ProductRepo
) : FlowUseCase<ShowProductsToSelect.Input, List<Product>>() {

    override fun execute(input: Input): Flow<List<Product>> {
        return if (input.applications == null) {
            productRepo.findAll()
        } else {
            productRepo.findByApplications(input.applications)
        }
    }

    data class Input(val applications: List<Product.Application>?)
}