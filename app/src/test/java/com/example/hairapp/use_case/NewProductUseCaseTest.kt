package com.example.hairapp.use_case

import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.example.hairapp.model.Product
import com.example.hairapp.model.ProductType
import com.example.hairapp.repository.ProductRepo
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NewProductUseCaseTest {
    private val productRepo: ProductRepo = mockk()

    private lateinit var useCase: NewProductUseCase

    @Before
    fun before() {
        coJustRun { productRepo.addNewProduct(any()) }
        useCase = NewProductUseCase(productRepo)
    }

    @Test
    fun productWithSameName_AlreadyExists() = runBlocking {
        // Arrange
        coEvery { productRepo.existsByName(any()) } returns true

        // Act
        val input = NewProductUseCase.Input(
            productName = "Shauma",
            productManufacturer = "Schwarzkopf",
            emollient = false,
            humectant = true,
            protein = false,
            productApplications = setOf("Mocny szampon")
        )
        val result = useCase.execute(input)

        // Assert
        assertThat(result).isInstanceOf(UseCaseResult.Error::class)
        val errorResult = result as UseCaseResult.Error
        assertThat(errorResult.exception).isInstanceOf(NewProductUseCase.ProductAlreadyExistsException::class)
        Unit
    }

    @Test
    fun newProductSaved() = runBlocking {
        // Arrange
        coEvery { productRepo.existsByName(any()) } returns false

        // Act
        val input = NewProductUseCase.Input(
            productName = "Shauma",
            productManufacturer = "Schwarzkopf",
            emollient = false,
            humectant = true,
            protein = false,
            productApplications = setOf("Mocny szampon")
        )
        val result = useCase.execute(input)

        // Assert
        assertThat(result).isInstanceOf(UseCaseResult.Success::class)
        val expectedNewProduct = Product(
            name = "Shauma",
            manufacturer = "Schwarzkopf",
            type = ProductType(emollient = false, humectant = true, protein = false),
            application = mutableSetOf("Mocny szampon")
        )
        coVerify { productRepo.addNewProduct(expectedNewProduct) }
        Unit
    }
}