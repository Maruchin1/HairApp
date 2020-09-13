package com.example.core.use_case

import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import com.example.core.domain.Product
import com.example.core.gateway.ProductRepo
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class DeleteProductTest {
    private val productRepo: ProductRepo = mockk()

    private lateinit var useCase: DeleteProduct

    @Before
    fun before() {
        useCase = DeleteProduct(productRepo)
    }

    @After
    fun after() {
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun productNotFound() = runBlocking {
        // Arrange
        coEvery { productRepo.findByName(any()) } returns flowOf()

        // Act
        val input = DeleteProduct.Input(productName = "test")
        val result = useCase(input)

        // Assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!).isInstanceOf(ProductException.NotFound::class)
        Unit
    }

    @Test
    fun productDeleteFromRepo() = runBlocking {
        // Arrange
        val product: Product = mockk()
        coEvery { productRepo.findByName(any()) } returns flowOf(product)
        coJustRun { productRepo.delete(any()) }

        // Act
        val input = DeleteProduct.Input(productName = "test")
        val result = useCase(input)

        // Assert
        assertThat(result.isSuccess).isTrue()
        coVerify { productRepo.delete(product) }
    }
}