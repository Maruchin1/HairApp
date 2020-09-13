package com.example.core.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.example.core.domain.Product
import com.example.core.gateway.ProductRepo
import io.mockk.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ShowSelectedProductTest {
    private val productRepo: ProductRepo = mockk()

    private lateinit var useCase: ShowSelectedProduct

    @Before
    fun before() {
        useCase = ShowSelectedProduct(productRepo)
    }

    @After
    fun after() {
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun returnFoundProduct() = runBlocking {
        // Arrange
        val product: Product = mockk()
        coEvery { productRepo.findByName(any()) } returns flowOf(product)

        // Act
        val input = ShowSelectedProduct.Input(productName = "test")
        val result = useCase(input).firstOrNull()

        // Assert
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(product)
    }

    @Test
    fun productNotFound() = runBlocking {
        // Arrange
        coEvery { productRepo.findByName(any()) } returns flowOf()

        // Act
        val input = ShowSelectedProduct.Input(productName = "test")
        val result = useCase(input).runCatching { collect() }

        // Assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!).isInstanceOf(ProductException.NotFound::class)
        Unit
    }
}