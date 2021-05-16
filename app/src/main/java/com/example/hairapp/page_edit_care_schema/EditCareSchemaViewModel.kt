package com.example.hairapp.page_edit_care_schema

import androidx.lifecycle.*
import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.use_case.DeleteCareSchema
import com.example.core.use_case.UpdateCareSchema
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditCareSchemaViewModel(
    private val updateCareSchema: UpdateCareSchema,
    private val deleteCareSchema: DeleteCareSchema
) : ViewModel() {

    private val _schemaName = MutableLiveData<String>()
    private val _schemaSteps = MutableLiveData<List<CareSchemaStep>>()
    private var selectedSchemaId: Int? = null

    val schemaName: LiveData<String> = _schemaName
    val schemaSteps: LiveData<List<CareSchemaStep>> = _schemaSteps

    suspend fun selectCareSchema(schemaId: Int): Result<Unit> {
        selectedSchemaId = schemaId
        return Result.success(Unit)
//        val input = ShowCareSchema.Input(schemaId)
//        return showCareSchema(input).runCatching {
//            applyData(first())
//        }
    }

    fun changeSchemaName(newName: String) {
        _schemaName.value = newName
    }

    suspend fun deleteSchema(): Result<Unit> {
        val schemaId = selectedSchemaId ?: return Result.failure(schemaNotSelected())
        val input = DeleteCareSchema.Input(schemaId)
        return deleteCareSchema(input)
    }

    fun saveChanges(updatedSteps: List<CareSchemaStep>) = GlobalScope.launch {
        val schemaId = selectedSchemaId ?: return@launch
        val updatedName = _schemaName.value ?: return@launch
        val input = UpdateCareSchema.Input(schemaId, updatedName, updatedSteps)
        updateCareSchema(input)
    }

    private fun applyData(schema: CareSchema) {
        _schemaName.postValue(schema.name)
        _schemaSteps.postValue(schema.steps)
    }

    private fun schemaNotSelected() = Exception("Schema not selected")
}