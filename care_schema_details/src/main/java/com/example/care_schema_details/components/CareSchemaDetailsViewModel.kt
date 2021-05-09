package com.example.care_schema_details.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.care_schema_details.use_case.ChangeSchemaNameUseCase
import com.example.care_schema_details.use_case.DeleteCareSchemaUseCase
import com.example.core.domain.CareSchema
import com.example.core.domain.CareStep
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CareSchemaDetailsViewModel(
    private val careSchemaId: Int,
    private val careSchemaRepo: CareSchemaRepo,
    private val changeSchemaNameUseCase: ChangeSchemaNameUseCase,
    private val deleteCareSchemaUseCase: DeleteCareSchemaUseCase
) : ViewModel() {

    private val careSchema: Flow<CareSchema> = careSchemaRepo.findById(careSchemaId)

    val schemaName: LiveData<String> = careSchema
        .map { it.name }
        .asLiveData()

    val schemaSteps: LiveData<List<CareStep>> = careSchema
        .map { it.steps }
        .asLiveData()

    suspend fun changeSchemaName(newName: String) {
        changeSchemaNameUseCase(careSchemaId, newName)
    }

    suspend fun deleteSchema() {
        deleteCareSchemaUseCase(careSchemaId)
    }
}