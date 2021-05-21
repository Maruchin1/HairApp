package com.example.corev2.navigation

import android.app.Activity
import javax.inject.Inject

class NavigationService @Inject constructor() {

    fun toDestination(originActivity: Activity, destination: Destination) {
        originActivity.startActivity(destination.makeIntent(originActivity))
    }
}