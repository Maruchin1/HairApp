package com.example.hairapp.page_edit_care_schema

import androidx.lifecycle.*
import com.example.core.domain.CareStep
import com.example.core.use_case.ShowCareSchema
import com.example.core.use_case.UpdateCareSchema

class EditCareSchemaViewModel(
    private val showCareSchema: ShowCareSchema,
    private val updateCareSchema: UpdateCareSchema
) : ViewModel() {

    private val _schemaName = MutableLiveData<String>()
    private val _schemaSteps = MutableLiveData<List<CareStep>>()

    val schemaName: LiveData<String> = _schemaName
    val schemaSteps: LiveData<List<CareStep>> = _schemaSteps


}