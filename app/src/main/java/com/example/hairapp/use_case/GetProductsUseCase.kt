package com.example.hairapp.use_case

import com.example.hairapp.model.Product
import com.example.hairapp.repository.ProductRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepo: ProductRepo
) : FlowUseCase<Unit, List<Product>>() {

    override fun execute(input: Unit): Flow<List<Product>> {
        return productRepo.findAllFlow()
    }
}