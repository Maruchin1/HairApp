package com.example.core.use_case

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isTrue
import com.example.core.gateway.Settings
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveManufacturerTest {
    private val settings: Settings = mockk()

    private lateinit var useCase: SaveManufacturer

    @Before
    fun before() {
        useCase = SaveManufacturer(settings)

        coJustRun { settings.addManufacturer(any()) }
    }

    @Test
    fun notSave_WhenAlreadySaved() = runBlocking {
        // Arrange
        every { settings.findSavedManufacturers() } returns flowOf(
            listOf("Nivea", "Avon", "Shwartzkopf")
        )

        // Act
        val input = SaveManufacturer.Input(manufacturer = "Avon")
        val result = useCase(input)

        // Assert
        assertThat(result.isSuccess).isTrue()
        coVerify(exactly = 0) { settings.addManufacturer("Avon") }
    }

    @Test
    fun saveNew_WhenNotAlreadySaved() = runBlocking {
        // Arrange
        every { settings.findSavedManufacturers() } returns flowOf(
            listOf("Nivea", "Shwartzkopf")
        )

        // Act
        val input = SaveManufacturer.Input(manufacturer = "Avon")
        val result = useCase(input)

        // Assert
        assertThat(result.isSuccess).isTrue()
        coVerify(exactly = 1) { settings.addManufacturer("Avon") }
    }

}