package com.example.hairapp.use_case

import com.example.hairapp.model.Product
import com.example.hairapp.model.ProductType
import com.example.hairapp.repository.ProductRepo

class AddNewProductUseCase(
    private val productRepo: ProductRepo
) : UseCase<AddNewProductUseCase.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun doWork(input: Input) {
        this.input = input
        if (alreadyExists())
            throw ProductAlreadyExistsException()
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
                emollient = input.isEmollient,
                humectant = input.isHumectant,
                protein = input.isProtein
            ),
            application = input.productApplications.toMutableSet()
        )
    }

    private suspend fun saveNewProduct(product: Product) {
        productRepo.addNewProduct(product)
    }

    data class Input(
        val productName: String,
        val productManufacturer: String,
        val isEmollient: Boolean,
        val isHumectant: Boolean,
        val isProtein: Boolean,
        val productApplications: Set<String>
    )

    class ProductAlreadyExistsException : IllegalStateException()
}