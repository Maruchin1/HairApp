package com.example.hairapp.framework

import android.content.Context
import android.widget.NumberPicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object Dialog {

    suspend fun pickNumber(
        context: Context,
        title: String,
        minValue: Int,
        maxValue: Int,
        currentValue: Int
    ): Int? = suspendCoroutine {
        val numberPicker = NumberPicker(context)
        numberPicker.minValue = minValue
        numberPicker.maxValue = maxValue
        numberPicker.value = currentValue

        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setView(numberPicker)
            .setPositiveButton("Zapisz") { _, _ ->
                it.resume(numberPicker.value)
            }
            .setNeutralButton("Zamknij") { _, _ ->
                it.resume(null)
            }
            .setOnCancelListener { _ ->
                it.resume(null)
            }
            .show()
    }
}