package com.example.core.use_case

import assertk.assertions.isInstanceOf
import com.example.core.base.UseCaseResult
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
        coJustRun { productRepo.addNewProduct(any()) }
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
            emollient = false,
            humectant = true,
            protein = false,
            productApplications = setOf("Mocny szampon"),
            productPhotoData = null
        )
        val result = useCase(input)

        // Assert
        assertk.assertThat(result).isInstanceOf(UseCaseResult.Error::class)
        val errorResult = result as UseCaseResult.Error
        assertk.assertThat(errorResult.exception).isInstanceOf(AddProduct.ProductAlreadyExists::class)
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
            emollient = false,
            humectant = true,
            protein = false,
            productApplications = setOf("Mocny szampon"),
            productPhotoData = null
        )
        val result = useCase(input)

        // Assert
        assertk.assertThat(result).isInstanceOf(UseCaseResult.Success::class)
        val expectedNewProduct = Product(
            name = "Shauma",
            manufacturer = "Schwarzkopf",
            type = ProductType(emollient = false, humectant = true, protein = false),
            application = mutableSetOf("Mocny szampon"),
            photoData = null
        )
        coVerify { productRepo.addNewProduct(expectedNewProduct) }
        Unit
    }
}