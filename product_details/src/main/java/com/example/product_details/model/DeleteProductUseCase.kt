package com.example.product_details.model

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.left
import arrow.core.right
import arrow.core.rightIfNotNull
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import kotlinx.coroutines.flow.firstOrNull

internal class DeleteProductUseCase(
    private val actions: Actions,
    private val productDao: ProductDao
) {

    suspend operator fun invoke(productId: Long): Either<Fail, Unit> = either {
        val productToDelete = findProduct(productId).bind()
        confirmProductDeletion().bind()
        productDao.delete(productToDelete)
        actions.closeProductDetails()
    }

    private suspend fun findProduct(productId: Long): Either<Fail, Product> =
        productDao.getById(productId)
            .firstOrNull()
            .rightIfNotNull { Fail.ProductNotFound }

    private suspend fun confirmProductDeletion(): Either<Fail, Unit> =
        actions.confirmProductDeletion().let {
            if (it) Unit.right() else Fail.DeletionNotConfirmed.left()
        }

    interface Actions {
        suspend fun confirmProductDeletion(): Boolean
        fun closeProductDetails()
    }

    sealed class Fail {
        object ProductNotFound : Fail()
        object DeletionNotConfirmed : Fail()
    }
}