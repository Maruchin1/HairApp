package com.example.common.navigation

import android.app.Activity
import android.content.Intent

abstract class Destination {

    protected abstract val activityClassname: String

    fun makeIntent(originActivity: Activity): Intent {
        val intent = Intent()
            .setClassName(originActivity, activityClassname)
        setupIntent(intent)
        return intent
    }

    protected open fun setupIntent(intent: Intent) = Unit
}