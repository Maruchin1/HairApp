package com.example.hairapp.framework

import android.app.Activity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.hairapp.R
import com.google.android.material.snackbar.Snackbar

object Snackbar {

    fun error(activity: Activity, exception: Throwable) {
        Snackbar.make(
            activity.findViewById<CoordinatorLayout>(R.id.coordinator),
            exception.message ?: "Wystąpił nieoczekiwany błąd",
            Snackbar.LENGTH_SHORT
        ).show()
    }
}