package com.example.edit_care_schema

import androidx.lifecycle.*
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.Product
import com.example.corev2.relations.CareSchemaWithSteps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class EditCareSchemaViewModel @Inject constructor(
    private val careSchemaDao: CareSchemaDao,
    private val careSchemaStepDao: CareSchemaStepDao
) : ViewModel() {

    private val careSchemaId = MutableStateFlow<Long?>(null)

    private val careSchemaWithStepsFlow: Flow<CareSchemaWithSteps?> = careSchemaId
        .filterNotNull()
        .flatMapLatest { careSchemaDao.getById(it) }

    val schemaName: LiveData<String> = careSchemaWithStepsFlow
        .filterNotNull()
        .map { it.careSchema.name }
        .asLiveData()

    val schemaSteps: LiveData<List<CareSchemaStep>> = careSchemaWithStepsFlow
        .filterNotNull()
        .map { it.steps }
        .map { getSortedByOrder(it) }
        .asLiveData()

    val noSteps: LiveData<Boolean> = schemaSteps.map { it.isEmpty() }

    suspend fun selectSchema(careSchemaId: Long) {
        this.careSchemaId.emit(careSchemaId)
    }

    suspend fun changeSchemaName(newName: String) {
        withCurrentCareSchema {
            val update = it.careSchema.copy(name = newName)
            careSchemaDao.update(update)
        }
    }

    suspend fun deleteSchema() {
        withCurrentCareSchema {
            careSchemaDao.delete(it.careSchema)
        }
    }

    suspend fun addStep(type: Product.Type) {
        withCurrentCareSchema {
            val newStep = CareSchemaStep(
                id = 0,
                prouctType = type,
                order = it.steps.size,
                careSchemaId = it.careSchema.id
            )
            careSchemaStepDao.insert(newStep)
        }
    }

    suspend fun updateSteps(steps: List<CareSchemaStep>) {
        careSchemaStepDao.update(*steps.toTypedArray())
    }

    suspend fun deleteStep(step: CareSchemaStep) {
        careSchemaStepDao.delete(step)
        withCurrentCareSchema {
            val mutableSteps = it.steps.toMutableList()
            mutableSteps.remove(step)
            mutableSteps.forEachIndexed { index, careSchemaStep ->
                careSchemaStep.order = index
            }
            careSchemaStepDao.update(*mutableSteps.toTypedArray())
        }
    }

    private suspend fun withCurrentCareSchema(action: suspend (CareSchemaWithSteps) -> Unit) {
        careSchemaWithStepsFlow
            .firstOrNull()
            ?.let { action(it) }
    }

    private fun getSortedByOrder(steps: List<CareSchemaStep>): List<CareSchemaStep> {
        return steps.sortedBy { it.order }
    }
}