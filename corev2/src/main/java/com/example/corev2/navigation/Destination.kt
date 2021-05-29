package com.example.corev2.navigation

import android.app.Activity
import android.content.Intent
import android.os.Parcelable

abstract class Destination<T : Parcelable> {

    protected abstract val activityClass: Class<*>

    fun navigate(originActivity: Activity, params: T? = null) {
        val intent = Intent(originActivity, activityClass)
            .putExtra(EXTRA_PARAMS, params)
        originActivity.startActivity(intent)
    }

    companion object {
        const val EXTRA_PARAMS = "extra_params"
    }
}