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

class CaptureProductPhotoUseCaseTest {
    private val actions = mockk<CaptureProductPhotoUseCase.Actions>()
    private val productDao = mockk<ProductDao>()
    private val captureProductPhotoUseCase by lazy {
        CaptureProductPhotoUseCase(actions, productDao)
    }

    private val productToAddPhoto = Product(id = 2, name = "Super shampoo")
    private val capturedPhotoData = "abc"

    @Before
    fun before() {
        every { productDao.getById(any()) } returns flowOf(null)
        every { productDao.getById(productToAddPhoto.id) } returns flowOf(productToAddPhoto)
        coEvery { actions.captureProductPhoto() } returns capturedPhotoData
        coJustRun { productDao.update(*anyVararg()) }
    }

    @Test
    fun productNotFound() = runBlocking {
        val result = captureProductPhotoUseCase(productId = 1)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(CaptureProductPhotoUseCase.Fail.ProductNotFound::class.java)
        }
        coVerify(exactly = 0) {
            productDao.update(*anyVararg())
        }
    }

    @Test
    fun photoNotCaptured() = runBlocking {
        coEvery { actions.captureProductPhoto() } returns null

        val result = captureProductPhotoUseCase(productId = 2)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(CaptureProductPhotoUseCase.Fail.PhotoNotCaptured::class.java)
        }
        coVerify(exactly = 0) {
            productDao.update(*anyVararg())
        }
    }

    @Test
    fun successfullyCaptured() = runBlocking {
        val result = captureProductPhotoUseCase(productId = 2)

        assertThat(result.isRight()).isTrue()
        coVerify {
            productDao.update(productToAddPhoto.copy(photoData = capturedPhotoData))
        }
    }
}