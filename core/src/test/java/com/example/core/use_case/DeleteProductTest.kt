package com.example.core.use_case

import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import com.example.core.domain.Product
import com.example.core.errors.ProductException
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
    fun productDeleteFromRepo() = runBlocking {
        // Arrange
        val product: Product = mockk()
        coJustRun { productRepo.delete(any()) }

        // Act
        val input = DeleteProduct.Input(productId = 1)
        val result = useCase(input)

        // Assert
        assertThat(result.isSuccess).isTrue()
        coVerify { productRepo.delete(product) }
    }
}