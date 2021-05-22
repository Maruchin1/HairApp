package com.example.edit_care_schema

import android.app.Activity
import android.content.Intent
import com.example.corev2.navigation.EditCareSchemaDestination
import javax.inject.Inject

internal class DestinationImpl @Inject constructor() : EditCareSchemaDestination() {

    override fun createIntent(originActivity: Activity, params: Params): Intent {
        return Intent(originActivity, EditCareSchemaActivity::class.java)
            .putExtra(EditCareSchemaActivity.CARE_SCHEMA_ID, params.careSchemaId)
    }
}