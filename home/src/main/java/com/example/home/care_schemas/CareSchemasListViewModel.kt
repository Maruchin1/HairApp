package com.example.home.care_schemas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.CareSchema
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CareSchemasListViewModel(
    private val careSchemaRepo: CareSchemaRepo
) : ViewModel() {

    private val allSchemas = careSchemaRepo.findAll()

    val careSchemas: LiveData<List<CareSchema>> = allSchemas
        .map { sortSchemasAlphabetically(it) }
        .asLiveData()

    private fun sortSchemasAlphabetically(schemas: List<CareSchema>): List<CareSchema> {
        return schemas.sortedBy { it.name }
    }
}