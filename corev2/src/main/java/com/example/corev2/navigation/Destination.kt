package com.example.corev2.navigation

import android.app.Activity
import android.content.Intent

abstract class Destination<T> {

    fun navigate(originActivity: Activity, params: T) {
        val intent = createIntent(originActivity, params)
        originActivity.startActivity(intent)
    }

    protected abstract fun createIntent(originActivity: Activity, params: T): Intent
}