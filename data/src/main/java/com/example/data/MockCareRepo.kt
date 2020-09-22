package com.example.data

import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import com.example.core.domain.ProductApplication
import com.example.core.gateway.CareRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MockCareRepo @Inject constructor() : CareRepo {

    private val collection = ConflatedBroadcastChannel(
        mutableListOf<Care>(
            Care(
                id = 1,
                type = Care.Type.OMO,
                date = LocalDate.now(),
                steps = listOf(
                    CareProduct(ProductApplication.Type.CONDITIONER),
                    CareProduct(ProductApplication.Type.SHAMPOO),
                    CareProduct(ProductApplication.Type.CONDITIONER),
                )
            )
        )
    )

    override suspend fun add(care: Care) {
        val state = collection.value
        val newCare = care.copy(
            id = state.size + 1
        )
        state.add(newCare)
        collection.send(state)
    }

    override suspend fun existsById(careId: Int): Boolean {
        return collection.value.find { it.id == careId } != null
    }

    override fun findAll(): Flow<List<Care>> {
        return collection.asFlow()
    }
}