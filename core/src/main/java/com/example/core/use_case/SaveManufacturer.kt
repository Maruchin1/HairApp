package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.gateway.Settings
import kotlinx.coroutines.flow.first

class SaveManufacturer(
    private val settings: Settings
) : UseCase<SaveManufacturer.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        if (notAlreadySaved()) {
            save()
        }
    }

    private suspend fun notAlreadySaved(): Boolean {
        val saved = settings.findSavedManufacturers().first()
        return !saved.contains(input.manufacturer)
    }

    private suspend fun save() {
        settings.addManufacturer(input.manufacturer)
    }

    data class Input(val manufacturer: String)
}