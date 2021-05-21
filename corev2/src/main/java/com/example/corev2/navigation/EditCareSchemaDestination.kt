package com.example.corev2.navigation

import android.content.Intent

data class EditCareSchemaDestination(
    private val careSchemaId: Long
) : Destination() {

    override val activityClassname: String
        get() = ACTIVITY_NAME

    override fun setupIntent(intent: Intent) {
        intent.putExtra(CARE_SCHEMA_ID, careSchemaId)
    }

    companion object {
        private const val ACTIVITY_NAME =
            "com.example.edit_care_schema.EditCareSchemaActivity"
        private const val CARE_SCHEMA_ID = "care_schema_id"
    }
}