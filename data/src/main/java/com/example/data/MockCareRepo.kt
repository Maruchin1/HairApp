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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
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
                photos = listOf(),
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
        collection.offer(state)
    }

    override suspend fun update(care: Care) {
        val state = collection.value
        state.removeIf { it.id == care.id }
        state.add(care)
        collection.offer(state)
    }

    override suspend fun existsById(careId: Int): Boolean {
        return collection.value.find { it.id == careId } != null
    }

    override fun findAll(): Flow<List<Care>> {
        return collection.asFlow()
    }

    override fun findById(careId: Int): Flow<Care> {
        return collection.asFlow()
            .map { list ->
                list.find { it.id == careId }
            }.filterNotNull()
    }
}