package com.example.data.repo

import android.content.Context
import android.util.Log
import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep
import com.example.core.gateway.CareSchemaRepo
import com.example.data.R
import com.example.data.dao.CareSchemaDao
import com.example.data.dao.CareSchemaStepDao
import com.example.data.entity.CareSchemaEntity
import com.example.data.entity.CareSchemaStepEntity
import com.example.data.room.Mapper
import com.example.data.room.mapList
import com.example.data.room.patch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class RoomCareSchemaRepo(
    private val context: Context,
    private val mapper: Mapper,
    private val careSchemaDao: CareSchemaDao,
    private val careSchemaStepDao: CareSchemaStepDao
) : CareSchemaRepo {

    init {
        checkInitialized()
    }

    override suspend fun addNew(careSchema: CareSchema): Int {
        val addedSchemaId = careSchemaDao.insert(CareSchemaEntity(careSchema)).first().toInt()
        val stepsEntities = careSchema.steps.map { CareSchemaStepEntity(it, addedSchemaId) }
        careSchemaStepDao.insert(stepsEntities)
        return addedSchemaId
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

    override fun findById(id: Int): Flow<CareSchema?> {
        return careSchemaDao.findById(id)
            .map { value -> value?.let { mapper.toDomain(it) } }
    }

    override fun findAll(): Flow<List<CareSchema>> {
        return careSchemaDao.findAll()
            .onEach { Log.d("RoomCareSchemaRepo", "$it") }
            .mapList { mapper.toDomain(it) }
    }

    private fun checkInitialized() = GlobalScope.launch {
        val schemas = careSchemaDao.findAll().first()
        if (schemas.isEmpty()) {
            getDefaultSchemas().forEach { addNew(it) }
        }
    }

    private fun getDefaultSchemas() = arrayOf(
        CareSchema(
            id = 0,
            name = context.getString(R.string.care_omo),
            steps = listOf(
                CareSchemaStep(type = CareStep.Type.CONDITIONER, order = 0),
                CareSchemaStep(type = CareStep.Type.SHAMPOO, order = 1),
                CareSchemaStep(type = CareStep.Type.CONDITIONER, order = 2)
            )
        ),
        CareSchema(
            id = 0,
            name = context.getString(R.string.care_cg),
            steps = listOf(
                CareSchemaStep(type = CareStep.Type.CONDITIONER, order = 0),
                CareSchemaStep(type = CareStep.Type.CONDITIONER, order = 1)
            )
        )
    )
}