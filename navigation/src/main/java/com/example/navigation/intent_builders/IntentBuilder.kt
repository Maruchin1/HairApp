package com.example.navigation.intent_builders

import android.app.Activity
import android.content.Intent

interface IntentBuilder {
    suspend fun buildIntent(originActivity: Activity): Intent
}