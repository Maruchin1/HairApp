package com.example.shared_ui

import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

abstract class BaseActionsHandler {

    private lateinit var activityRef: WeakReference<AppCompatActivity>

    protected val activity: AppCompatActivity?
        get() = activityRef.get()

    fun bind(activity: AppCompatActivity) {
        activityRef = WeakReference(activity)
    }
}