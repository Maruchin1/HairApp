package com.example.data.repo

import com.example.core.domain.Care
import com.example.core.gateway.CareRepo
import com.example.data.dao.CareDao
import com.example.data.dao.CarePhotoDao
import com.example.data.dao.CareStepDao
import com.example.data.entity.CareEntity
import com.example.data.entity.CarePhotoEntity
import com.example.data.entity.CareStepEntity
import com.example.data.room.Mapper
import com.example.data.room.mapList
import com.example.data.room.patch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

internal data class RoomCareRepo(
    private val mapper: Mapper,
    private val careDao: CareDao,
    private val carePhotoDao: CarePhotoDao,
    private val careStepDao: CareStepDao
) : CareRepo {

    override suspend fun add(care: Care) {
        val addedCareId = careDao.insert(CareEntity(care)).first().toInt()
        val photosEntities = care.photos.map { CarePhotoEntity(it, addedCareId) }
        val productsEntities = care.steps.map { CareStepEntity(it, addedCareId) }
        carePhotoDao.insert(photosEntities)
        careStepDao.insert(productsEntities)
    }

    override suspend fun update(care: Care) {
        careDao.update(CareEntity(care))
        carePhotoDao.patch(
            newData = care.photos.map { CarePhotoEntity(it, care.id) },
            existingData = carePhotoDao.findByCare(care.id).first()
        )
        careStepDao.patch(
            newData = care.steps.map { CareStepEntity(it, care.id) },
            existingData = careStepDao.findByCare(care.id).first()
        )
    }

    override fun findAll(): Flow<List<Care>> {
        return careDao.findAll()
            .mapList { mapper.toDomain(it) }
    }

    override fun findById(careId: Int): Flow<Care> {
        return careDao.findById(careId)
            .map { mapper.toDomain(it) }
    }
}