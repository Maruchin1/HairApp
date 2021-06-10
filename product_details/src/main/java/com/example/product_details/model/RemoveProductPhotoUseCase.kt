package com.example.product_details.model

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.left
import arrow.core.right
import arrow.core.rightIfNotNull
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import kotlinx.coroutines.flow.firstOrNull

internal class RemoveProductPhotoUseCase(
    private val actions: Actions,
    private val productDao: ProductDao
) {

    suspend operator fun invoke(productId: Long): Either<Fail, Unit> = either {
        val productToUpdate = findProduct(productId).bind()
        confirmProductPhotoDeletion().bind()
        val update = productToUpdate.copy(photoData = null)
        updateProductInDb(update)
    }

    private suspend fun findProduct(productId: Long): Either<Fail, Product> =
        productDao.getById(productId)
            .firstOrNull()
            .rightIfNotNull { Fail.ProductNotFound }

    private suspend fun confirmProductPhotoDeletion(): Either<Fail, Unit> {
        val confirmed = actions.confirmProductPhotoDeletion()
        return if (confirmed) {
            Unit.right()
        } else {
            Fail.DeletionNotConfirmed.left()
        }
    }

    private suspend fun updateProductInDb(product: Product) =
        productDao.update(product)

    interface Actions {
        suspend fun confirmProductPhotoDeletion(): Boolean
    }

    sealed class Fail {
        object ProductNotFound : Fail()
        object DeletionNotConfirmed : Fail()
    }
}