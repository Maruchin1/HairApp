package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.Application
import com.example.core.gateway.ProductApplicationRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowProductApplicationOptions @Inject constructor(
    private val productApplicationRepo: ProductApplicationRepo
) : FlowUseCase<Unit, List<Application>>() {

    override fun execute(input: Unit): Flow<List<Application>> {
        return productApplicationRepo.findAll()
    }
}