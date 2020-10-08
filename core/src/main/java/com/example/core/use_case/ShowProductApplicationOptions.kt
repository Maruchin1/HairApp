package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.Application
import com.example.core.gateway.ApplicationRepo
import kotlinx.coroutines.flow.Flow

class ShowProductApplicationOptions(
    private val applicationRepo: ApplicationRepo
) : FlowUseCase<Unit, List<Application>>() {

    override fun execute(input: Unit): Flow<List<Application>> {
        return applicationRepo.findAll()
    }
}