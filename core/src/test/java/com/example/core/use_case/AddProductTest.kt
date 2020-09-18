package com.example.core.use_case

import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import com.example.core.domain.Product
import com.example.core.domain.ProductApplication
import com.example.core.domain.ProductType
import com.example.core.errors.ProductException
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
        // Arrange
        coEvery { productRepo.existsByName(any()) } returns false

        // Act
        val input = AddProduct.Input(
            productName = "Shauma",
            productManufacturer = "Schwarzkopf",
            emollients = false,
            humectants = true,
            proteins = false,
            productApplications = setOf(
                ProductApplication("Mocny szampon", ProductApplication.Type.SHAMPOO)
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
            type = ProductType(emollients = false, humectants = true, proteins = false),
            applications = mutableSetOf(
                ProductApplication("Mocny szampon", ProductApplication.Type.SHAMPOO)
            ),
            photoData = null
        )
        coVerify { productRepo.add(expectedNewProduct) }
        Unit
    }
}