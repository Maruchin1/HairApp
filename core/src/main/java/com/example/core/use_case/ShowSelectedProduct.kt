package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.Product
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

class ShowSelectedProduct @Inject constructor(
    private val productRepo: ProductRepo
) : FlowUseCase<ShowSelectedProduct.Input, Product>() {

    override fun execute(input: Input): Flow<Product> {
        return productRepo.findByNameFlow(input.productName)
            .onEmpty { throw SelectedProductNotFound() }
    }


    data class Input(val productName: String)

    class SelectedProductNotFound : IllegalStateException()
}