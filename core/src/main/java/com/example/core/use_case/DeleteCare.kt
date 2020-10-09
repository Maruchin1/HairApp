package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.Care
import com.example.core.errors.CareException
import com.example.core.gateway.CareRepo
import kotlinx.coroutines.flow.firstOrNull

class DeleteCare(
    private val careRepo: CareRepo
) : UseCase<DeleteCare.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        val careToDelete = findCare() ?: throw CareException.NotFound(input.careId)
        deleteCare(careToDelete)
    }

    private suspend fun findCare() = careRepo.findById(input.careId).firstOrNull()

    private suspend fun deleteCare(care: Care) = careRepo.delete(care)

    class Input(val careId: Int)
}