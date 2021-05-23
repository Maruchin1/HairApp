package com.example.corev2.room_database

import android.content.Context
import com.example.corev2.R
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.data_store.InitializationCompletionStore
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.ProductType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class DatabaseInitializerImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val initializationCompletionStore: InitializationCompletionStore,
    private val careSchemaDao: CareSchemaDao,
    private val careSchemaStepDao: CareSchemaStepDao
) : DatabaseInitializer {

    override fun checkIfInitialized() = GlobalScope.launch {
        val isInitialized = initializationCompletionStore.databaseInitializedFlow.first()
        if (!isInitialized) {
            initializeDatabase()
            initializationCompletionStore.setDatabaseInitialized(true)
        }
    }

    private suspend fun initializeDatabase() {
        populateCareSchemas()
    }

    private suspend fun populateCareSchemas() {
        val addedIds = careSchemaDao.insert(createOmoSchema(), createCgSchema())
        val omoSchemaId = addedIds[0]
        val cgSchemaId = addedIds[1]
        careSchemaStepDao.insert(*createOmoSteps(omoSchemaId), *createCgSteps(cgSchemaId))
    }

    private fun createOmoSchema() = CareSchema(
        id = 0,
        name = context.getString(R.string.omo)
    )

    private fun createCgSchema() = CareSchema(
        id = 0,
        name = context.getString(R.string.cg)
    )

    private fun createOmoSteps(omoSchemaId: Long) = arrayOf(
        CareSchemaStep(
            id = 0,
            prouctType = ProductType.CONDITIONER,
            order = 0,
            careSchemaId = omoSchemaId
        ),
        CareSchemaStep(
            id = 0,
            prouctType = ProductType.SHAMPOO,
            order = 1,
            careSchemaId = omoSchemaId
        ),
        CareSchemaStep(
            id = 0,
            prouctType = ProductType.CONDITIONER,
            order = 2,
            careSchemaId = omoSchemaId
        )
    )

    private fun createCgSteps(cgSchemaId: Long) = arrayOf(
        CareSchemaStep(
            id = 0,
            prouctType = ProductType.CONDITIONER,
            order = 0,
            careSchemaId = cgSchemaId
        ),
        CareSchemaStep(
            id = 0,
            prouctType = ProductType.CONDITIONER,
            order = 1,
            careSchemaId = cgSchemaId
        ),
    )
}