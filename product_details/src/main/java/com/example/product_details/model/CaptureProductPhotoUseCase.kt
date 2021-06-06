package com.example.product_details.model

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.rightIfNotNull
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import kotlinx.coroutines.flow.firstOrNull

internal class CaptureProductPhotoUseCase(
    private val actions: Actions,
    private val productDao: ProductDao
) {

    suspend operator fun invoke(productId: Long): Either<Fail, Unit> {
        return either {
            val product = getProductToAddPhoto(productId).bind()
            val capturedPhoto = captureProductPhoto().bind()
            val update = product.copy(photoData = capturedPhoto)
            updateProductInDb(update)
        }
    }

    private suspend fun getProductToAddPhoto(productId: Long): Either<Fail, Product> =
        productDao.getById(productId)
            .firstOrNull()
            .rightIfNotNull { Fail.ProductNotFound }

    private suspend fun captureProductPhoto(): Either<Fail, String> =
        actions.captureProductPhoto()
            .rightIfNotNull { Fail.PhotoNotCaptured }

    private suspend fun updateProductInDb(product: Product) =
        productDao.update(product)

    interface Actions {
        suspend fun captureProductPhoto(): String?
    }

    sealed class Fail {
        object ProductNotFound : Fail()
        object PhotoNotCaptured : Fail()
    }
}