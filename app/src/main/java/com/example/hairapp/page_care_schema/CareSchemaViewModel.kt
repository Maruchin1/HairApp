package com.example.hairapp.page_care_schema

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.base.invoke
import com.example.core.domain.CareStep
import com.example.core.use_case.SaveCareSchema
import com.example.core.use_case.ShowCareSchema
import kotlinx.coroutines.flow.first

class CareSchemaViewModel(
    private val showCareSchema: ShowCareSchema,
    private val saveCareSchema: SaveCareSchema
) : ViewModel() {

    val careSchema: LiveData<List<CareStep>> = showCareSchema().asLiveData()

    suspend fun schemaChanged(newSchema: List<CareStep>): Boolean {
        val originalSchema = showCareSchema().first()
        return originalSchema != newSchema
    }

    suspend fun saveChanges(newSchema: List<CareStep>): Result<Unit> {
        val input = SaveCareSchema.Input(newSchema)
        return saveCareSchema(input)
    }
}