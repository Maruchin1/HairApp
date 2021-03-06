package com.example.core.use_case

import assertk.assertThat
import assertk.assertions.isTrue
import com.example.core.domain.Product
import com.example.core.gateway.ProductRepo
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class AddProductTest {
    private val productRepo: ProductRepo = mockk()

    private lateinit var useCase: AddProduct

    @Before
    fun before() {
        coJustRun { productRepo.add(any()) }
        useCase = AddProduct(productRepo)
    }

    @After
    fun after() {
        unmockkAll()
        clearAllMocks()
    }

    @Test
    fun newProductSaved() = runBlocking {
        // Act
        val input = AddProduct.Input(
            productName = "Shauma",
            productManufacturer = "Schwarzkopf",
            emollients = false,
            humectants = true,
            proteins = false,
            applications = setOf(
                Product.Application.STRONG_SHAMPOO
            ),
            productPhotoData = null
        )
        val result = useCase(input)

        // Assert
        assertThat(result.isSuccess).isTrue()
        val expectedNewProduct = Product(
            id = 0,
            name = "Shauma",
            manufacturer = "Schwarzkopf",
            composition = Product.Composition(
                emollients = false,
                humectants = true,
                proteins = false
            ),
            applications = mutableSetOf(
                Product.Application.STRONG_SHAMPOO
            ),
            photoData = null
        )
        coVerify { productRepo.add(expectedNewProduct) }
        Unit
    }
}