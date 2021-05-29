package com.example.corev2.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class EditCareSchemaDestination(
    override val activityClass: Class<*>
) : Destination<EditCareSchemaDestination.Params>() {

    @Parcelize
    data class Params(val careSchemaId: Long) : Parcelable

    companion object {
        const val ACTIVITY = "edit_care_schema_activity"
    }
}