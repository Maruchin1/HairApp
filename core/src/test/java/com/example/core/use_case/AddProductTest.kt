package com.example.core.use_case

import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import com.example.core.domain.Product
import com.example.core.domain.ProductType
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
        coJustRun { productRepo.addNew(any()) }
        useCase = AddProduct(productRepo)
    }

    @After
    fun after() {
        unmockkAll()
        clearAllMocks()
    }

    @Test
    fun productWithSameName_AlreadyExists() = runBlocking {
        // Arrange
        coEvery { productRepo.existsByName(any()) } returns true

        // Act
        val input = AddProduct.Input(
            productName = "Shauma",
            productManufacturer = "Schwarzkopf",
            emollients = false,
            humectants = true,
            proteins = false,
            productApplications = setOf("Mocny szampon"),
            productPhotoData = null
        )
        val result = useCase(input)

        // Assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!).isInstanceOf(ProductException.AlreadyExists::class)
        Unit
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
            productApplications = setOf("Mocny szampon"),
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
            application = mutableSetOf("Mocny szampon"),
            photoData = null
        )
        coVerify { productRepo.addNew(expectedNewProduct) }
        Unit
    }
}