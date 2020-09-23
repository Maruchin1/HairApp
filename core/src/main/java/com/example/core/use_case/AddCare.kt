package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import com.example.core.gateway.CareRepo
import java.time.LocalDate
import javax.inject.Inject

class AddCare @Inject constructor(
    private val careRepo: CareRepo
) : UseCase<AddCare.Input, Unit>() {

    override suspend fun execute(input: Input) {
        val newCare = makeNewCare(input)
        saveCare(newCare)
    }

    private fun makeNewCare(input: Input): Care {
        return Care(
            id = 0,
            type = input.type,
            date = input.date,
            photos = input.photos,
            steps = input.steps
        )
    }

    private suspend fun saveCare(care: Care) {
        careRepo.add(care)
    }

    data class Input(
        val date: LocalDate,
        val type: Care.Type,
        val photos: List<String>,
        val steps: List<CareProduct>
    )
}