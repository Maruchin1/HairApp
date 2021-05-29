package com.example.corev2.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class CareDetailsDestination(
    override val activityClass: Class<*>
) : Destination<CareDetailsDestination.Params>() {

    @Parcelize
    data class Params(val careId: Long) : Parcelable

    companion object {
        const val ACTIVITY = "care_details_activity"
    }
}