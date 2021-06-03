package com.example.navigation.base

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.inlineactivityresult.coroutines.startActivityAwaitResult
import com.example.navigation.EXTRA_DESTINATION_RESULT
import com.example.navigation.intent_builders.IntentBuilder

class DestinationWithResult<O : Parcelable>(
    private val defaultResult: O,
    private val intentBuilder: IntentBuilder
) {

    suspend fun navigate(originActivity: AppCompatActivity): O {
        val intent = intentBuilder.buildIntent(originActivity)
        val result = originActivity.startActivityAwaitResult(intent)
        return if (result.success) {
            result.data.getParcelableExtra(EXTRA_DESTINATION_RESULT) ?: defaultResult
        } else {
            defaultResult
        }
    }

}