package com.example.core.use_case

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.example.core.domain.Product
import com.example.core.gateway.ProductRepo
import com.example.core.invoke
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ShowProductsListTest {
    private val productRepo: ProductRepo = mockk()

    private lateinit var useCase: ShowProductsList

    @Before
    fun before() {
        useCase = ShowProductsList(productRepo)
    }

    @After
    fun after() {
        unmockkAll()
        clearAllMocks()
    }

    @Test
    fun returnAllProductsFromRepo() = runBlocking {
        // Arrange
        val products = listOf<Product>(mockk(), mockk())
        every { productRepo.findAllFlow() } returns flowOf(products)

        // Act
        val result = useCase().first()

        // Assert
        assertThat(result).isEqualTo(products)
    }

    @Test
    fun returnChangedList() = runBlocking {
        // Arrange
        val products = listOf<Product>(mockk(), mockk())
        val changedProducts = listOf<Product>(mockk())
        every { productRepo.findAllFlow() } returns flow {
            emit(products)
            delay(100)
            emit(changedProducts)
        }

        // Act
        val results = useCase().toList()

        // Assert
        assertThat(results).hasSize(2)
        assertThat(results[0]).isEqualTo(products)
        assertThat(results[1]).isEqualTo(changedProducts)
    }
}