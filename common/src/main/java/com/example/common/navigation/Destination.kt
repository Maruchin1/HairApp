package com.example.common.navigation

import android.app.Activity
import android.content.Intent

internal sealed class Destination {

    protected abstract val activityClassname: String

    fun makeIntent(originActivity: Activity): Intent {
        val intent = Intent()
            .setClassName(originActivity, activityClassname)
        setupIntent(intent)
        return intent
    }

    protected abstract fun setupIntent(intent: Intent)

    data class CareSchemaDetails(
        val careSchemaId: Int
    ) : Destination() {

        override val activityClassname: String
            get() = CARE_SCHEMA_DETAILS_ACTIVITY

        override fun setupIntent(intent: Intent) {
            intent.putExtra(CARE_SCHEMA_ID, careSchemaId)
        }
    }

    companion object {
        const val CARE_SCHEMA_DETAILS_ACTIVITY =
            "com.example.care_schema_details.CareSchemaDetailsActivity"
        const val CARE_SCHEMA_ID = "care_schema_id"
    }
}