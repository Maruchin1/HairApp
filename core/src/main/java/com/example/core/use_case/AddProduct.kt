package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.Product
import com.example.core.domain.ProductType
import com.example.core.gateway.ProductRepo
import javax.inject.Inject

class AddProduct @Inject constructor(
    private val productRepo: ProductRepo
) : UseCase<AddProduct.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        if (alreadyExists())
            throw ProductException.AlreadyExists(input.productName)
        val newProduct = makeNewProduct()
        saveNewProduct(newProduct)
    }

    private suspend fun alreadyExists(): Boolean {
        return productRepo.existsByName(input.productName)
    }

    private fun makeNewProduct(): Product {
        return Product(
            name = input.productName,
            manufacturer = input.productManufacturer,
            type = ProductType(
                emollients = input.emollient,
                humectants = input.humectant,
                proteins = input.protein
            ),
            application = input.productApplications.toMutableSet(),
            photoData = input.productPhotoData
        )
    }

    private suspend fun saveNewProduct(product: Product) {
        productRepo.addNew(product)
    }

    data class Input(
        val productName: String,
        val productManufacturer: String,
        val emollient: Boolean,
        val humectant: Boolean,
        val protein: Boolean,
        val productApplications: Set<String>,
        val productPhotoData: String?
    )
}