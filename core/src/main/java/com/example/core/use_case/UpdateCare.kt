package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.Care
import com.example.core.domain.CareStep
import com.example.core.errors.CareException
import com.example.core.gateway.CareRepo
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate

class UpdateCare(
    private val careRepo: CareRepo
) : UseCase<UpdateCare.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        val existingCare = findCare() ?: throw CareException.NotFound(input.careId)
        applyUpdate(existingCare)
        saveUpdated(existingCare)
    }

    private suspend fun findCare() = careRepo.findById(input.careId).firstOrNull()

    private fun applyUpdate(care: Care) = care.apply {
        date = input.date
        photos = input.photos
        steps = input.steps
    }

    private suspend fun saveUpdated(care: Care) = careRepo.update(care)

    data class Input(
        val careId: Int,
        val date: LocalDate,
        val photos: List<String>,
        val steps: List<CareStep>
    )
}