package com.example.corev2.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class CareDetailsDestination(
    override val activityClass: Class<*>
) : Destination<CareDetailsDestination.Params>() {

    sealed class Params : Parcelable {

        @Parcelize
        data class AddNewCare(val careSchemaId: Long) : Params()

        @Parcelize
        data class OpenCare(val careId: Long) : Params()
    }

    companion object {
        const val ACTIVITY = "care_details_activity"
    }
}