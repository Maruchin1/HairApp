package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.Product
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UpdateProduct @Inject constructor(
    private val productRepo: ProductRepo
) : UseCase<UpdateProduct.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        val existingProduct = findProduct() ?: throw ProductException.NotFound(input.productName)
        dispatchUpdate(existingProduct)
        saveUpdated(existingProduct)
    }

    private suspend fun findProduct() = productRepo.findByName(input.productName).firstOrNull()

    private fun dispatchUpdate(product: Product) {
        product.apply {
            name = input.productName
            manufacturer = input.productManufacturer
            type.apply {
                humectants = input.humectants
                emollients = input.emollients
                proteins = input.proteins
            }
            application = input.productApplications
            photoData = input.productPhotoData
        }
    }

    private suspend fun saveUpdated(product: Product) = productRepo.update(product)

    data class Input(
        val productName: String,
        val productManufacturer: String,
        val humectants: Boolean,
        val emollients: Boolean,
        val proteins: Boolean,
        val productApplications: Set<String>,
        val productPhotoData: String?
    )
}