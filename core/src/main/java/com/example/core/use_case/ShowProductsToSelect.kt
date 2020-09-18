package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.Product
import com.example.core.domain.ProductApplication
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowProductsToSelect @Inject constructor(
    private val productRepo: ProductRepo
) : FlowUseCase<ShowProductsToSelect.Input, List<Product>>() {

    override fun execute(input: Input): Flow<List<Product>> {
        return if (input.productApplicationType == null) {
            productRepo.findAll()
        } else {
            productRepo.findByApplicationType(input.productApplicationType)
        }
    }

    data class Input(val productApplicationType: ProductApplication.Type?)
}