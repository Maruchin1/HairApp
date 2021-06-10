package com.example.product_details.model

import arrow.core.handleError
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Ingredients
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

class UpdateProductUseCaseTest {
    private val productDao = mockk<ProductDao>()
    private val updateProductUseCase by lazy {
        UpdateProductUseCase(productDao)
    }

    private val productToUpdate = Product(id = 2)
    private val input = UpdateProductUseCase.Input(
        newProductName = "Test name",
        newManufacturer = "Test manufacturer",
        newIngredients = Ingredients(proteins = true, humectants = true),
        newApplications = setOf(
            Product.Application.STRONG_SHAMPOO
        )
    )

    @Before
    fun before() {
        coEvery { productDao.getById(any()) } returns flowOf(null)
        coEvery { productDao.getById(productToUpdate.id) } returns flowOf(productToUpdate)
        coJustRun { productDao.update(*anyVararg()) }
    }

    @Test
    fun productNotFound() = runBlocking {
        val result = updateProductUseCase(productId = 1, input = input)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(UpdateProductUseCase.Fail.ProductNotFound::class.java)
        }
        coVerify(exactly = 0) {
            productDao.update(*anyVararg())
        }
    }

    @Test
    fun successfullyUpdated() = runBlocking {
        val result = updateProductUseCase(productId = 2, input = input)

        assertThat(result.isRight()).isTrue()
        coVerify {
            productDao.update(
                productToUpdate.copy(
                    name = input.newProductName,
                    manufacturer = input.newManufacturer,
                    ingredients = input.newIngredients,
                    applications = input.newApplications
                )
            )
        }
    }
}