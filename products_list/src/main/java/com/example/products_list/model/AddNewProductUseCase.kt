package com.example.products_list.model

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.right
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product

internal class AddNewProductUseCase(
    private val actions: Actions,
    private val productDao: ProductDao
) {

    suspend operator fun invoke(): Either<Fail, Unit> = either {
        val newProduct = createNewProduct()
        val newProductId = insertProductToDb(newProduct).bind()
        openProductDetails(newProductId)
    }

    private fun createNewProduct() = Product()

    private suspend fun insertProductToDb(product: Product) =
        productDao.insert(product)
            .first()
            .right()

    private suspend fun openProductDetails(productId: Long) =
        actions.openProductDetails(productId)

    interface Actions {
        suspend fun openProductDetails(productId: Long)
    }

    sealed class Fail {

    }
}