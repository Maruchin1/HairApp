package com.example.product_details.model

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.rightIfNotNull
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import kotlinx.coroutines.flow.firstOrNull

internal class ChangeProductBasicInfoUseCase(
    private val productDao: ProductDao
) {

    suspend operator fun invoke(
        productId: Long,
        newName: String,
        newManufacturer: String
    ): Either<Fail, Unit> = either {
        val productToUpdate = getProductToUpdate(productId).bind()
        val update = productToUpdate.copy(name = newName, manufacturer = newManufacturer)
        updateProductInDb(update)
    }

    private suspend fun getProductToUpdate(productId: Long): Either<Fail, Product> =
        productDao.getById(productId)
            .firstOrNull()
            .rightIfNotNull { Fail.ProductNotFound }


    private suspend fun updateProductInDb(product: Product) =
        productDao.update(product)

    sealed class Fail {
        object ProductNotFound : Fail()
    }
}