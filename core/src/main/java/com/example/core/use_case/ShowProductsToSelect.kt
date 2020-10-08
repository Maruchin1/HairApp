package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.Product
import com.example.core.domain.Application
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.flow.Flow

class ShowProductsToSelect(
    private val productRepo: ProductRepo
) : FlowUseCase<ShowProductsToSelect.Input, List<Product>>() {

    override fun execute(input: Input): Flow<List<Product>> {
        return if (input.applicationType == null) {
            productRepo.findAll()
        } else {
            productRepo.findByApplicationType(input.applicationType)
        }
    }

    data class Input(val applicationType: Application.Type?)
}