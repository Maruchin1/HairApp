package com.example.hairapp.use_case

import android.util.Log
import com.example.hairapp.model.Product
import com.example.hairapp.model.ProductType
import com.example.hairapp.repository.ProductRepo
import javax.inject.Inject

class NewProductUseCase @Inject constructor(
    private val productRepo: ProductRepo
) : UseCase<NewProductUseCase.Input, Unit>() {
    private val TAG = "NewProductUseCase"

    private lateinit var input: Input

    override suspend fun doWork(input: Input) {
        Log.d(TAG, "doWork")
        Log.d(TAG, "$input")
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
                emollient = input.emollient,
                humectant = input.humectant,
                protein = input.protein
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
        val emollient: Boolean,
        val humectant: Boolean,
        val protein: Boolean,
        val productApplications: Set<String>
    )

    class ProductAlreadyExistsException : IllegalStateException()
}