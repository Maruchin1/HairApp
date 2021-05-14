package com.example.common.navigation

import android.app.Activity

class AppNavigator {

    fun toDestination(originActivity: Activity, destination: Destination) {
        originActivity.startActivity(destination.makeIntent(originActivity))
    }
}