package com.example.product_details.model

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.rightIfNotNull
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Ingredients
import com.example.corev2.entities.Product
import kotlinx.coroutines.flow.firstOrNull

internal class UpdateProductUseCase(
    private val productDao: ProductDao
) {

    suspend operator fun invoke(productId: Long, input: Input): Either<Fail, Unit> = either {
        val productToUpdate = findProduct(productId).bind()
        val update = productToUpdate.copy(
            name = input.newProductName,
            manufacturer = input.newManufacturer,
            ingredients = input.newIngredients,
            applications = input.newApplications
        )
        productDao.update(update)
    }

    private suspend fun findProduct(productId: Long) =
        productDao.getById(productId)
            .firstOrNull()
            .rightIfNotNull { Fail.ProductNotFound }

    sealed class Fail {
        object ProductNotFound : Fail()
    }

    data class Input(
        val newProductName: String = "",
        val newManufacturer: String = "",
        val newIngredients: Ingredients = Ingredients(),
        val newApplications: Set<Product.Application> = emptySet()
    )
}