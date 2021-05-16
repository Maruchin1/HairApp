package com.example.edit_care_schema.components

import androidx.lifecycle.*
import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.*

internal class EditCareSchemaViewModel(
    private val careSchemaId: Int,
    private val careSchemaRepo: CareSchemaRepo,
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