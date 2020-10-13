package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.CareSchema
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.Flow

class ShowCareSchemas(
    private val careSchemaRepo: CareSchemaRepo
) : FlowUseCase<Unit, List<CareSchema>>() {

    override fun execute(input: Unit): Flow<List<CareSchema>> {
        return careSchemaRepo.findAll()
    }

}