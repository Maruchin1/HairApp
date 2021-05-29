package com.example.corev2.navigation

class HomeDestination(override val activityClass: Class<*>) : Destination<Nothing>() {

    companion object {
        const val ACTIVITY = "home_activity"
    }
}