package com.example.data

import com.example.core.domain.Application
import com.example.core.gateway.ProductApplicationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockProductApplicationRepo @Inject constructor() : ProductApplicationRepo {

    private val applications = listOf(
        Application("Łagodny szampon", Application.Type.SHAMPOO),
        Application("Średni szampon", Application.Type.SHAMPOO),
        Application("Mocny szampon", Application.Type.SHAMPOO),
        Application("Odżywka", Application.Type.CONDITIONER),
        Application("Krem", Application.Type.OTHER),
        Application("Maska", Application.Type.OTHER),
        Application("Odżywka b/s", Application.Type.OTHER),
        Application("Olej", Application.Type.OTHER),
        Application("Pianka", Application.Type.OTHER),
        Application("Serum", Application.Type.OTHER),
        Application("Żel", Application.Type.OTHER),
        Application("Inny", Application.Type.OTHER)
    )

    override fun findAll(): Flow<List<Application>> {
        return flowOf(applications)
    }

}