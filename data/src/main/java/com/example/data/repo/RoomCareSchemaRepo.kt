package com.example.data.repo

import com.example.core.domain.CareSchema
import com.example.core.gateway.CareSchemaRepo
import com.example.data.dao.CareSchemaDao
import com.example.data.dao.CareSchemaStepDao
import com.example.data.entity.CareSchemaEntity
import com.example.data.entity.CareSchemaStepEntity
import com.example.data.room.Mapper
import com.example.data.room.mapList
import com.example.data.room.patch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class RoomCareSchemaRepo(
    private val mapper: Mapper,
    private val careSchemaDao: CareSchemaDao,
    private val careSchemaStepDao: CareSchemaStepDao
) : CareSchemaRepo {

    override suspend fun addNew(careSchema: CareSchema) {
        val addedSchemaId = careSchemaDao.insert(CareSchemaEntity(careSchema)).first().toInt()
        val stepsEntities = careSchema.steps.map { CareSchemaStepEntity(it, addedSchemaId) }
        careSchemaStepDao.insert(stepsEntities)
    }

    override suspend fun update(careSchema: CareSchema) {
        careSchemaDao.update(CareSchemaEntity(careSchema))
        careSchemaStepDao.patch(
            newData = careSchema.steps.map { CareSchemaStepEntity(it, careSchema.id) },
            existingData = careSchemaStepDao.findByCareSchema(careSchema.id).first()
        )
    }

    override suspend fun delete(careSchema: CareSchema) {
        careSchemaDao.delete(CareSchemaEntity(careSchema))
    }

    override fun findById(id: Int): Flow<CareSchema> {
        return careSchemaDao.findById(id)
            .map { mapper.toDomain(it) }
    }

    override fun findByName(name: String): Flow<CareSchema> {
        return careSchemaDao.findByName(name)
            .map { mapper.toDomain(it) }
    }

    override fun findAll(): Flow<List<CareSchema>> {
        return careSchemaDao.findAll()
            .mapList { mapper.toDomain(it) }
    }


}