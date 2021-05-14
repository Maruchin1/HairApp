package com.example.home.care_schemas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.CareSchema
import com.example.core.gateway.CareSchemaRepo

internal class CareSchemasListViewModel(
    private val careSchemaRepo: CareSchemaRepo
) : ViewModel() {

    val careSchemas: LiveData<List<CareSchema>> = careSchemaRepo.findAll().asLiveData()
}