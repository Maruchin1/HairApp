package com.example.care_schemas_list

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.navigation.CareSchemaDetailsDestination
import com.example.navigation.CareSchemaDetailsParams
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class CareSchemasListViewModel(
    private val careSchemaDao: CareSchemaDao,
    private val careSchemaDetailsDestination: CareSchemaDetailsDestination
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

    fun openSchema(activity: Activity, careSchemaId: Long) = viewModelScope.launch {
        val params = CareSchemaDetailsParams(careSchemaId)
        careSchemaDetailsDestination.navigate(activity, params)
    }

    private fun sortSchemasAlphabetically(
        schemas: List<CareSchemaWithSteps>
    ): List<CareSchemaWithSteps> {
        return schemas.sortedBy { it.careSchema.name }
    }
}