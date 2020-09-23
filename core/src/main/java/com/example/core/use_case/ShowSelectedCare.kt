package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.Care
import com.example.core.errors.CareException
import com.example.core.gateway.CareRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

class ShowSelectedCare @Inject constructor(
    private val careRepo: CareRepo
) : FlowUseCase<ShowSelectedCare.Input, Care>() {

    override fun execute(input: Input): Flow<Care> {
        return careRepo.findById(input.careId)
            .onEmpty { throw CareException.NotFound(input.careId) }
    }

    data class Input(val careId: Int)
}