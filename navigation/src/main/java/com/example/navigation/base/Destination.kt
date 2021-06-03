package com.example.navigation.base

import android.app.Activity
import com.example.navigation.intent_builders.IntentBuilder

open class Destination(
    private val intentBuilder: IntentBuilder
) {

    suspend fun navigate(originActivity: Activity) {
        val intent = intentBuilder.buildIntent(originActivity)
        originActivity.startActivity(intent)
    }
}