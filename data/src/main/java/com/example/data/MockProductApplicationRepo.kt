package com.example.data

import com.example.core.domain.ProductApplication
import com.example.core.gateway.ProductApplicationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockProductApplicationRepo @Inject constructor() : ProductApplicationRepo {

    private val applications = listOf(
        ProductApplication("Łagodny szampon", ProductApplication.Type.SHAMPOO),
        ProductApplication("Średni szampon", ProductApplication.Type.SHAMPOO),
        ProductApplication("Mocny szampon", ProductApplication.Type.SHAMPOO),
        ProductApplication("Odżywka", ProductApplication.Type.CONDITIONER),
        ProductApplication("Krem", ProductApplication.Type.OTHER),
        ProductApplication("Maska", ProductApplication.Type.OTHER),
        ProductApplication("Odżywka b/s", ProductApplication.Type.OTHER),
        ProductApplication("Olej", ProductApplication.Type.OTHER),
        ProductApplication("Pianka", ProductApplication.Type.OTHER),
        ProductApplication("Serum", ProductApplication.Type.OTHER),
        ProductApplication("Żel", ProductApplication.Type.OTHER),
        ProductApplication("Inny", ProductApplication.Type.OTHER)
    )

    override fun findAll(): Flow<List<ProductApplication>> {
        return flowOf(applications)
    }

}