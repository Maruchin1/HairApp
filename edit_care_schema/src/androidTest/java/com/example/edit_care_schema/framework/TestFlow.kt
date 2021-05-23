package com.example.edit_care_schema.framework

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.Product
import com.example.corev2.room_database.HairAppDatabase
import com.example.edit_care_schema.EditCareSchemaActivity
import kotlinx.coroutines.runBlocking

internal fun testFlow(
    hairAppDatabase: HairAppDatabase,
    test: suspend (scenario: ActivityScenario<EditCareSchemaActivity>, careSchemaId: Long) -> Unit
) = runBlocking {
    val careSchemaId = populateSchemaInDatabase(hairAppDatabase)
    val scenario = startActivity(careSchemaId)
    test(scenario, careSchemaId)
    scenario.close()
}

private fun startActivity(careSchemaId: Long): ActivityScenario<EditCareSchemaActivity> {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val intent = Intent(context, EditCareSchemaActivity::class.java)
        .putExtra(EditCareSchemaActivity.CARE_SCHEMA_ID, careSchemaId)
    return ActivityScenario.launch(intent)
}

private fun populateSchemaInDatabase(hairAppDatabase: HairAppDatabase): Long = runBlocking {
    val addedSchemaId = hairAppDatabase.careSchemaDao().insert(
        CareSchema(id = 0, name = "OMO")
    ).first()
    hairAppDatabase.careSchemaStepDao().insert(
        CareSchemaStep(
            id = 0,
            prouctType = Product.Type.CONDITIONER,
            order = 0,
            careSchemaId = addedSchemaId
        ),
        CareSchemaStep(
            id = 0,
            prouctType = Product.Type.SHAMPOO,
            order = 1,
            careSchemaId = addedSchemaId
        ),
        CareSchemaStep(
            id = 0,
            prouctType = Product.Type.CONDITIONER,
            order = 2,
            careSchemaId = addedSchemaId
        ),
    )
    addedSchemaId
}