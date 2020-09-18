package com.example.data

import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import com.example.core.gateway.CareRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import java.time.LocalDate
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MockCareRepo @Inject constructor() : CareRepo {

    private val collection = ConflatedBroadcastChannel(
        mutableListOf<Care>(
            Care.OMO(
                id = 1,
                date = LocalDate.now(),
                firstConditioner = CareProduct(),
                shampoo = CareProduct(),
                secondConditioner = CareProduct()
            )
        )
    )

    override suspend fun add(care: Care) {
        val state = collection.value
        state.add(care)
        collection.send(state)
    }

    override suspend fun existsById(careId: Int): Boolean {
        return collection.value.find { it.id == careId } != null
    }
}