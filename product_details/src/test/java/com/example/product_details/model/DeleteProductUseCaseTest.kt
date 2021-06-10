package com.example.product_details.model

import arrow.core.handleError
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteProductUseCaseTest {
    private val actions = mockk<DeleteProductUseCase.Actions>()
    private val productDao = mockk<ProductDao>()
    private val deleteProductUseCase by lazy {
        DeleteProductUseCase(actions, productDao)
    }

    private val productToDelete = Product(id = 2, name = "Test")

    @Before
    fun before() {
        coEvery { productDao.getById(any()) } returns flowOf(null)
        coEvery { productDao.getById(productToDelete.id) } returns flowOf(productToDelete)
        coJustRun { productDao.delete(*anyVararg()) }
        coEvery { actions.confirmProductDeletion() } returns true
        justRun { actions.closeProductDetails() }
    }

    @Test
    fun productNotFound() = runBlocking {
        val result = deleteProductUseCase(1)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(DeleteProductUseCase.Fail.ProductNotFound::class.java)
        }
        coVerify(exactly = 0) {
            productDao.delete(*anyVararg())
        }
    }

    @Test
    fun deletionNotConfirmed() = runBlocking {
        coEvery { actions.confirmProductDeletion() } returns false

        val result = deleteProductUseCase(2)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(DeleteProductUseCase.Fail.DeletionNotConfirmed::class.java)
        }
        coVerify(exactly = 0) {
            productDao.delete(*anyVararg())
        }
    }

    @Test
    fun successfullyDeleted() = runBlocking {
        val result = deleteProductUseCase(2)

        assertThat(result.isRight()).isTrue()
        coVerify {
            productDao.delete(productToDelete)
            actions.closeProductDetails()
        }
    }
}