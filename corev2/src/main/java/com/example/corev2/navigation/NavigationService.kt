package com.example.corev2.navigation

import android.app.Activity

interface NavigationService {

    fun toDestination(originActivity: Activity, destination: Destination)
}