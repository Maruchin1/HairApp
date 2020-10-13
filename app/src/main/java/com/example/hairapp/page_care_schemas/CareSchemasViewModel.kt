package com.example.hairapp.page_care_schemas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.base.invoke
import com.example.core.domain.CareSchema
import com.example.core.use_case.AddCareSchema
import com.example.core.use_case.ShowCareSchemas

class CareSchemasViewModel(
    private val showCareSchemas: ShowCareSchemas,
    private val addCareSchema: AddCareSchema
) : ViewModel() {

    val careSchemas: LiveData<List<CareSchema>> = showCareSchemas().asLiveData()

    suspend fun addCareSchema(name: String): Result<Int> {
        val input = AddCareSchema.Input(name)
        return addCareSchema(input)
    }
}