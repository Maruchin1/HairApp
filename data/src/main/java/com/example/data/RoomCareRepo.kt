package com.example.data

import com.example.core.domain.Care
import com.example.core.gateway.CareRepo
import com.example.data.dao.CareDao
import com.example.data.dao.ProductDao
import com.example.data.entity.CarePhotoEntity
import com.example.data.room.Mapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class RoomCareRepo @Inject constructor(
    private val mapper: Mapper,
    private val careDao: CareDao,
    private val productDao: ProductDao
) : CareRepo {

    override suspend fun add(care: Care) {

    }

    override suspend fun update(care: Care) {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flow<List<Care>> {
        TODO("Not yet implemented")
    }

    override fun findById(careId: Int): Flow<Care> {
        TODO("Not yet implemented")
    }
}