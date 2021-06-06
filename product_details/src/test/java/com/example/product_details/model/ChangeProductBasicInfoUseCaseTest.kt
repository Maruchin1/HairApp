package com.example.product_details.model

import arrow.core.handleError
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.google.common.truth.Truth.assertThat
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ChangeProductBasicInfoUseCaseTest {
    private val productDao = mockk<ProductDao>()
    private val changeProductBasicInfoUseCase by lazy {
        ChangeProductBasicInfoUseCase(productDao)
    }

    private val productToUpdate = Product(
        id = 2,
        name = "name",
        manufacturer = "manufacturer"
    )
    private val newName = "new name"
    private val newManufacturer = "new manufacturer"

    @Before
    fun before() {
        every { productDao.getById(2) } returns flowOf(productToUpdate)
        coJustRun { productDao.update(*anyVararg()) }
    }

    @Test
    fun productNotFound() = runBlocking {
        val result = changeProductBasicInfoUseCase(1, newName, newManufacturer)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(ChangeProductBasicInfoUseCase.Fail.ProductNotFound::class.java)
        }
        coVerify(exactly = 0) {
            productDao.update(*anyVararg())
        }
    }

    @Test
    fun successfullyChangedBasicInfo() = runBlocking {
        val result = changeProductBasicInfoUseCase(2, newName, newManufacturer)

        assertThat(result.isRight()).isTrue()
        coVerify {
            productDao.update(productToUpdate.copy(name = newName, manufacturer = newManufacturer))
        }
    }
}