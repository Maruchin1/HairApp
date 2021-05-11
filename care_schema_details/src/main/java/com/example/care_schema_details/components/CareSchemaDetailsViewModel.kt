package com.example.care_schema_details.components

import androidx.lifecycle.*
import com.example.care_schema_details.use_case.ChangeSchemaNameUseCase
import com.example.care_schema_details.use_case.ChangeSchemaStepsUseCase
import com.example.care_schema_details.use_case.DeleteCareSchemaUseCase
import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class CareSchemaDetailsViewModel(
    private val careSchemaId: Int,
    private val careSchemaRepo: CareSchemaRepo,
    private val changeSchemaNameUseCase: ChangeSchemaNameUseCase,
    private val changeSchemaStepsUseCase: ChangeSchemaStepsUseCase,
    private val deleteCareSchemaUseCase: DeleteCareSchemaUseCase
) : ViewModel() {

    private val careSchema: Flow<CareSchema> = careSchemaRepo.findById(careSchemaId)
    private val _stepsEditModeEnabled = MutableStateFlow(false)

    val schemaName: LiveData<String> = careSchema
        .map { it.name }
        .asLiveData()

    val schemaSteps: LiveData<List<CareSchemaStep>> = careSchema
        .map { it.steps }
        .asLiveData()

    val stepsEditModeEnabled: LiveData<Boolean> = _stepsEditModeEnabled.asLiveData()

    suspend fun changeSchemaName(newName: String) {
        changeSchemaNameUseCase(careSchemaId, newName)
    }

    suspend fun deleteSchema() {
        deleteCareSchemaUseCase(careSchemaId)
    }

    suspend fun getSchemaName(): String {
        return careSchema.first().name
    }

    fun isStepsEditModeEnabled(): Boolean {
        return _stepsEditModeEnabled.value
    }

    fun enableStepsEditMode() = viewModelScope.launch {
        _stepsEditModeEnabled.emit(true)
    }

    suspend fun saveStepsChanges(changedSteps: List<CareSchemaStep>) {
        changeSchemaStepsUseCase(careSchemaId, changedSteps)
        _stepsEditModeEnabled.emit(false)
    }
}