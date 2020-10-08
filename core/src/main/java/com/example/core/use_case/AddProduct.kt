package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.Product
import com.example.core.domain.Application
import com.example.core.gateway.ProductRepo
import javax.inject.Inject

class AddProduct @Inject constructor(
    private val productRepo: ProductRepo
) : UseCase<AddProduct.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        val newProduct = makeNewProduct()
        saveNewProduct(newProduct)
    }

    private fun makeNewProduct(): Product {
        return Product(
            id = 0,
            name = input.productName,
            manufacturer = input.productManufacturer,
            type = Product.Type(
                emollients = input.emollients,
                humectants = input.humectants,
                proteins = input.proteins
            ),
            applications = input.applications.toMutableSet(),
            photoData = input.productPhotoData
        )
    }

    private suspend fun saveNewProduct(product: Product) {
        productRepo.add(product)
    }

    data class Input(
        val productName: String,
        val productManufacturer: String,
        val humectants: Boolean,
        val emollients: Boolean,
        val proteins: Boolean,
        val applications: Set<Application>,
        val productPhotoData: String?
    )
}