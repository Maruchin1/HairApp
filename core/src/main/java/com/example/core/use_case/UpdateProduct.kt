package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.Product
import com.example.core.errors.ProductException
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.flow.firstOrNull

class UpdateProduct(
    private val productRepo: ProductRepo
) : UseCase<UpdateProduct.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        val existingProduct = findProduct() ?: throw ProductException.NotFound(input.productId)
        applyUpdate(existingProduct)
        saveUpdated(existingProduct)
    }

    private suspend fun findProduct() = productRepo.findById(input.productId).firstOrNull()

    private fun applyUpdate(product: Product) {
        product.apply {
            name = input.productName
            manufacturer = input.productManufacturer
            composition.apply {
                humectants = input.humectants
                emollients = input.emollients
                proteins = input.proteins
            }
            applications = input.applications
            photoData = input.productPhotoData
        }
    }

    private suspend fun saveUpdated(product: Product) = productRepo.update(product)

    data class Input(
        val productId: Int,
        val productName: String,
        val productManufacturer: String,
        val humectants: Boolean,
        val emollients: Boolean,
        val proteins: Boolean,
        val applications: Set<Product.Application>,
        val productPhotoData: String?
    )
}