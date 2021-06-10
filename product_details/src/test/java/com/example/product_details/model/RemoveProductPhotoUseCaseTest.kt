package com.example.product_details.model

import arrow.core.handleError
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RemoveProductPhotoUseCaseTest {
    private val actions = mockk<RemoveProductPhotoUseCase.Actions>()
    private val productDao = mockk<ProductDao>()
    private val removeProductPhotoUseCase by lazy {
        RemoveProductPhotoUseCase(actions, productDao)
    }

    private val productToUpdate = Product(
        id = 2,
        photoData = "abc"
    )

    @Before
    fun before() {
        coEvery { productDao.getById(any()) } returns flowOf(null)
        coEvery { productDao.getById(productToUpdate.id) } returns flowOf(productToUpdate)
        coEvery { actions.confirmProductPhotoDeletion() } returns true
        coJustRun { productDao.update(*anyVararg()) }
    }

    @Test
    fun productNotFound() = runBlocking {
        val result = removeProductPhotoUseCase(productId = 1)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(RemoveProductPhotoUseCase.Fail.ProductNotFound::class.java)
        }
        coVerify(exactly = 0) {
            productDao.update(*anyVararg())
        }
    }

    @Test
    fun deletionNotConfirmed() = runBlocking {
        coEvery { actions.confirmProductPhotoDeletion() } returns false

        val result = removeProductPhotoUseCase(productId = 2)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(RemoveProductPhotoUseCase.Fail.DeletionNotConfirmed::class.java)
        }
        coVerify(exactly = 0) {
            productDao.update(*anyVararg())
        }
    }

    @Test
    fun successfullyRemoved() = runBlocking {
        val result = removeProductPhotoUseCase(productId = 2)

        assertThat(result.isRight()).isTrue()
        coVerify {
            productDao.update(productToUpdate.copy(photoData = null))
        }
    }
}