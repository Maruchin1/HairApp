package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.Care
import com.example.core.domain.CareStep
import com.example.core.gateway.CareRepo
import java.time.LocalDate

class AddCare(
    private val careRepo: CareRepo
) : UseCase<AddCare.Input, Unit>() {

    override suspend fun execute(input: Input) {
        val newCare = makeNewCare(input)
        saveCare(newCare)
    }

    private fun makeNewCare(input: Input): Care {
        return Care(
            id = 0,
            schemaName = input.schemaName,
            date = input.date,
            photos = input.photos,
            steps = input.steps,
            notes = input.notes
        )
    }

    private suspend fun saveCare(care: Care) {
        careRepo.add(care)
    }

    data class Input(
        val schemaName: String,
        val date: LocalDate,
        val photos: List<String>,
        val steps: List<CareStep>,
        val notes: String
    )
}