package com.example.corev2.navigation

import android.app.Activity
import javax.inject.Inject

internal class NavigationServiceImpl @Inject constructor() : NavigationService {

    override fun toDestination(originActivity: Activity, destination: Destination) {
        originActivity.startActivity(destination.makeIntent(originActivity))
    }
}