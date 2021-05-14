package com.example.common.navigation

class HomeDestination : Destination() {

    override val activityClassname: String
        get() = ACTIVITY_NAME

    companion object {
        private const val ACTIVITY_NAME = "com.example.home.HomeActivity"
    }
}