package com.example.room_database.transactions

import com.example.core.domain.CareSchema
import com.example.room_database.dao.CareSchemaDao
import com.example.room_database.dao.CareSchemaStepDao
import com.example.room_database.entities.CareSchemaEntity
import com.example.room_database.entities.CareSchemaStepEntity
import com.example.room_database.mappers.toEntity

internal class AddNewCareSchemaTransaction(
    private val careSchemaDao: CareSchemaDao,
    private val careSchemaStepDao: CareSchemaStepDao
) {

    suspend operator fun invoke(careSchema: CareSchema): Int {
        val addedSchemaId = insertSchema(careSchema.toEntity())
        val stepsEntities = careSchema.steps.map { it.toEntity(addedSchemaId) }
        insertSteps(stepsEntities)
        return addedSchemaId
    }

    private suspend fun insertSchema(entity: CareSchemaEntity): Int {
        return careSchemaDao.insert(entity).first().toInt()
    }

    private suspend fun insertSteps(entities: List<CareSchemaStepEntity>) {
        careSchemaStepDao.insert(*entities.toTypedArray())
    }


}