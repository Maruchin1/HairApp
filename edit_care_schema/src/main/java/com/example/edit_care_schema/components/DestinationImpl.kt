package com.example.edit_care_schema.components

import android.app.Activity
import android.content.Intent
import com.example.corev2.navigation.EditCareSchemaDestination

internal class DestinationImpl : EditCareSchemaDestination() {

    override fun createIntent(originActivity: Activity, params: Params): Intent {
        return Intent(originActivity, EditCareSchemaActivity::class.java)
            .putExtra(EditCareSchemaActivity.CARE_SCHEMA_ID, params.careSchemaId)
    }
}