package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.gateway.CareRepo

class DeleteCarePhoto(
    private val careRepo: CareRepo
) : UseCase<DeleteCarePhoto.Input, Unit>() {

    override suspend fun execute(input: Input) {
        careRepo.deleteCarePhoto(input.data)
    }

    data class Input(val data: String)
}