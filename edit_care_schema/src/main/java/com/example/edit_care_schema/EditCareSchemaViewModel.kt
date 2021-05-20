package com.example.edit_care_schema

import androidx.lifecycle.*
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.relations.CareSchemaWithSteps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EditCareSchemaViewModel @Inject constructor(
    private val careSchemaDao: CareSchemaDao,
    private val careSchemaStepDao: CareSchemaStepDao
) : ViewModel() {

    private val careSchemaId = MutableStateFlow<Long?>(null)

    private val careSchemaWithSteps: SharedFlow<CareSchemaWithSteps?> = careSchemaId
        .filterNotNull()
        .flatMapLatest { careSchemaDao.getById(it) }
        .shareIn(viewModelScope, SharingStarted.Lazily)

    val schemaName: LiveData<String> = careSchemaWithSteps
        .filterNotNull()
        .map { it.careSchema.name }
        .asLiveData()

    val schemaSteps: LiveData<List<CareSchemaStep>> = careSchemaWithSteps
        .filterNotNull()
        .map { it.steps }
        .asLiveData()

    val noSteps: LiveData<Boolean> = schemaSteps.map { it.isEmpty() }

    suspend fun selectSchema(careSchemaId: Long) {
        this.careSchemaId.emit(careSchemaId)
    }

    suspend fun getSchemaName(): String {
        return careSchemaWithSteps.first()!!.careSchema.name
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

    suspend fun addStep(type: CareSchemaStep.Type) {
        withCurrentCareSchema {
            val newStep = CareSchemaStep(
                id = 1,
                type = type,
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
    }

    private suspend fun withCurrentCareSchema(action: suspend (CareSchemaWithSteps) -> Unit) {
        careSchemaWithSteps.firstOrNull()?.let { action(it) }
    }
}