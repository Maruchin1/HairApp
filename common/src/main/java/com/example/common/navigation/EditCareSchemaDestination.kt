package com.example.common.navigation

import android.content.Intent

data class EditCareSchemaDestination(
    private val careSchemaId: Int
) : Destination() {

    override val activityClassname: String
        get() = ACTIVITY_NAME

    override fun setupIntent(intent: Intent) {
        intent.putExtra(CARE_SCHEMA_ID, careSchemaId)
    }

    companion object {
        private const val ACTIVITY_NAME =
            "com.example.care_schema_details.EditCareSchemaActivity"
        private const val CARE_SCHEMA_ID = "care_schema_id"
    }
}