package com.example.home

import android.app.Activity
import android.content.Intent
import com.example.corev2.navigation.HomeDestination
import javax.inject.Inject

internal class DestinationImpl @Inject constructor() : HomeDestination() {

    override fun createIntent(originActivity: Activity, params: Unit): Intent {
        return Intent(originActivity, HomeActivity::class.java)
    }
}