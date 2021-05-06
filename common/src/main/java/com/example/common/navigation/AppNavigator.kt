package com.example.common.navigation

import android.app.Activity
import android.content.Intent

class AppNavigator {

    fun toCareSchemaDetails(originActivity: Activity, careSchemaId: Int) {
        val destination = Destination.CareSchemaDetails(careSchemaId)
        startDestination(originActivity, destination)
    }

    private fun startDestination(originActivity: Activity, destination: Destination) {
        originActivity.startActivity(destination.makeIntent(originActivity))
    }
}