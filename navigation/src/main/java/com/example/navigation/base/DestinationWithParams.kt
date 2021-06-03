package com.example.navigation.base

import android.app.Activity
import android.os.Parcelable
import com.example.navigation.EXTRA_DESTINATION_PARAMS
import com.example.navigation.intent_builders.IntentBuilder

class DestinationWithParams<T : Parcelable>(
    private val intentBuilder: IntentBuilder
) {

    suspend fun navigate(originActivity: Activity, params: T) {
        val intent = intentBuilder.buildIntent(originActivity)
            .putExtra(EXTRA_DESTINATION_PARAMS, params)
        originActivity.startActivity(intent)
    }
}