package com.example.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import com.example.core.domain.Application
import com.example.core.gateway.CareRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockCareRepo @Inject constructor() : CareRepo {

    private val collection = MutableLiveData(
        mutableListOf(
            Care(
                id = 1,
                type = Care.Type.OMO,
                date = LocalDate.now(),
                photos = listOf(),
                steps = listOf(
                    CareProduct(Application.Type.CONDITIONER),
                    CareProduct(Application.Type.SHAMPOO),
                    CareProduct(Application.Type.CONDITIONER),
                )
            )
        )
    )

    override suspend fun add(care: Care) = collection.updateState {
        val newCare = care.copy(
            id = it.size + 1
        )
        it.add(newCare)
    }

    override suspend fun update(care: Care) = collection.updateState {
        it.removeIf { item -> item.id == care.id }
        it.add(care)
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