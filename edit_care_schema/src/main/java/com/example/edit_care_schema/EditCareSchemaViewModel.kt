package com.example.edit_care_schema

import androidx.lifecycle.*
import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.gateway.CareSchemaRepo
import com.example.edit_care_schema.use_case.*
import com.example.edit_care_schema.use_case.AddSchemaStepUseCase
import com.example.edit_care_schema.use_case.ChangeSchemaNameUseCase
import com.example.edit_care_schema.use_case.ChangeSchemaStepsUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaStepUseCase
import kotlinx.coroutines.flow.*

internal class EditCareSchemaViewModel(
    private val careSchemaId: Int,
    private val careSchemaRepo: CareSchemaRepo,
    val changeSchemaName: ChangeSchemaNameUseCase,
    val changeSchemaSteps: ChangeSchemaStepsUseCase,
    val deleteSchema: DeleteSchemaUseCase,
    val addSchemaStep: AddSchemaStepUseCase,
    val deleteSchemaStep: DeleteSchemaStepUseCase
) : ViewModel() {

    private val careSchema: Flow<CareSchema?> = careSchemaRepo.findById(careSchemaId)

    val schemaName: LiveData<String> = careSchema
        .filterNotNull()
        .map { it.name }
        .asLiveData()

    val schemaSteps: LiveData<List<CareSchemaStep>> = careSchema
        .filterNotNull()
        .map { it.steps }
        .asLiveData()

    val noSteps: LiveData<Boolean> = schemaSteps.map { it.isEmpty() }

    suspend fun getSchemaName(): String {
        return careSchema.first()!!.name
    }
}