package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.Product
import com.example.core.errors.ProductException
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DeleteProduct @Inject constructor(
    private val productRepo: ProductRepo
) : UseCase<DeleteProduct.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        val productToDelete = findProduct() ?: throw ProductException.NotFound(input.productId)
        deleteProduct(productToDelete)
    }

    private suspend fun findProduct() = productRepo.findById(input.productId).firstOrNull()

    private suspend fun deleteProduct(product: Product) = productRepo.delete(product)

    class Input(val productId: Int)
}