package com.example.data.repo

import com.example.core.domain.Care
import com.example.core.gateway.CareRepo
import com.example.data.dao.CareDao
import com.example.data.dao.ProductDao
import com.example.data.room.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal data class RoomCareRepo(
    private val mapper: Mapper,
    private val careDao: CareDao,
    private val productDao: ProductDao
) : CareRepo {

    override suspend fun add(care: Care) {

    }

    override suspend fun update(care: Care) {
    }

    override fun findAll(): Flow<List<Care>> {
        return flowOf()
    }

    override fun findById(careId: Int): Flow<Care> {
        return flowOf()
    }
}