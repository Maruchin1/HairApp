package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.ProductApplication
import com.example.core.gateway.ProductApplicationRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowProductApplicationOptions @Inject constructor(
    private val productApplicationRepo: ProductApplicationRepo
) : FlowUseCase<Unit, List<ProductApplication>>() {

    override fun execute(input: Unit): Flow<List<ProductApplication>> {
        return productApplicationRepo.findAll()
    }
}