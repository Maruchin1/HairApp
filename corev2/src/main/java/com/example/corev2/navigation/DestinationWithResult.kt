package com.example.corev2.navigation

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.inlineactivityresult.coroutines.startActivityAwaitResult

abstract class DestinationWithResult<I : Parcelable, O> {

    protected abstract val activityClass: Class<*>

    suspend fun navigate(activity: AppCompatActivity, params: I? = null): O? {
        val intent = Intent(activity, activityClass)
            .putExtra(EXTRA_PARAMS, params)
        val result = activity.startActivityAwaitResult(intent)
        return if (result.success) {
            getResultFromIntent(result.data)
        } else {
            null
        }
    }

    protected abstract fun getResultFromIntent(intent: Intent): O?

    companion object {
        const val EXTRA_PARAMS = "extra_params"
    }
}