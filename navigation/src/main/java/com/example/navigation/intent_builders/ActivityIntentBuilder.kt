package com.example.navigation.intent_builders

import android.app.Activity
import android.content.Intent

internal class ActivityIntentBuilder(
    private val activityClass: Class<*>
) : IntentBuilder {
    override suspend fun buildIntent(originActivity: Activity): Intent {
        return Intent(originActivity, activityClass)
    }
}