package com.example.room_database.transactions

import com.example.core.domain.CareSchema
import com.example.room_database.dao.CareSchemaDao
import com.example.room_database.dao.CareSchemaStepDao
import com.example.room_database.entities.CareSchemaEntity
import com.example.room_database.entities.CareSchemaStepEntity
import com.example.room_database.mappers.toEntity
import kotlinx.coroutines.flow.first

internal class UpdateCareSchemaTransaction(
    private val careSchemaDao: CareSchemaDao,
    private val careSchemaStepDao: CareSchemaStepDao
) {

    suspend operator fun invoke(careSchema: CareSchema) {
        updateSchema(careSchema.toEntity())
        patchSteps(careSchema)
    }

    private suspend fun updateSchema(entity: CareSchemaEntity) {
        careSchemaDao.update(entity)
    }

    private suspend fun patchSteps(careSchema: CareSchema) {
        val existingSteps = careSchemaStepDao.findByCareSchema(careSchema.id).first()
        val newSteps = careSchema.steps.map { it.toEntity(careSchema.id) }
        updateSteps(existingSteps, newSteps)
        deleteSteps(existingSteps, newSteps)
        addSteps(existingSteps, newSteps)
    }

    private suspend fun updateSteps(
        existingSteps: List<CareSchemaStepEntity>,
        newSteps: List<CareSchemaStepEntity>
    ) {
        val stepsToUpdate = newSteps.filter { newStep ->
            val existingStep = existingSteps.find { existingStep ->
                existingStep.id == newStep.id
            }
            existingStep != null && existingStep != newStep
        }
        careSchemaStepDao.update(*stepsToUpdate.toTypedArray())
    }

    private suspend fun deleteSteps(
        existingSteps: List<CareSchemaStepEntity>,
        newSteps: List<CareSchemaStepEntity>
    ) {
        val stepsToDelete = existingSteps.filter { !newSteps.containsStep(it) }
        careSchemaStepDao.delete(*stepsToDelete.toTypedArray())
    }

    private suspend fun addSteps(
        existingSteps: List<CareSchemaStepEntity>,
        newSteps: List<CareSchemaStepEntity>
    ) {
        val stepsToAdd = newSteps.filter { !existingSteps.containsStep(it) }
        careSchemaStepDao.insert(*stepsToAdd.toTypedArray())
    }

    private fun List<CareSchemaStepEntity>.containsStep(step: CareSchemaStepEntity): Boolean {
        return any { it.id == step.id }
    }
}