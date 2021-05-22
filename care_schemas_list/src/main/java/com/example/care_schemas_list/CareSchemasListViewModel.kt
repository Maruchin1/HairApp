package com.example.care_schemas_list

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.navigation.EditCareSchemaDestination
import com.example.corev2.relations.CareSchemaWithSteps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class CareSchemasListViewModel @Inject constructor(
    private val careSchemaDao: CareSchemaDao,
    private val editCareSchemaDestination: EditCareSchemaDestination
) : ViewModel() {

    private val allSchemas: Flow<List<CareSchemaWithSteps>> = careSchemaDao.getAll()

    val careSchemas: LiveData<List<CareSchemaWithSteps>> = allSchemas
        .map { sortSchemasAlphabetically(it) }
        .asLiveData()

    suspend fun addNewSchema(schemaName: String) {
        val newSchema = CareSchema(
            id = 0,
            name = schemaName
        )
        careSchemaDao.insert(newSchema)
    }

    fun openSchema(activity: Activity, careSchemaId: Long) {
        val params = EditCareSchemaDestination.Params(careSchemaId)
        editCareSchemaDestination.navigate(activity, params)
    }

    private fun sortSchemasAlphabetically(
        schemas: List<CareSchemaWithSteps>
    ): List<CareSchemaWithSteps> {
        return schemas.sortedBy { it.careSchema.name }
    }
}