package com.example.edit_care_schema.components

import androidx.lifecycle.*
import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.gateway.CareSchemaRepo
import com.example.edit_care_schema.use_case.ChangeSchemaNameUseCase
import com.example.edit_care_schema.use_case.ChangeSchemaStepsUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaUseCase
import kotlinx.coroutines.flow.*

internal class EditCareSchemaViewModel(
    private val careSchemaId: Int,
    private val careSchemaRepo: CareSchemaRepo,
    val changeSchemaName: ChangeSchemaNameUseCase,
    val changeSchemaSteps: ChangeSchemaStepsUseCase,
    val deleteSchema: DeleteSchemaUseCase
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