package com.example.navigation

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T : Parcelable> destinationParams() =
    ReadOnlyProperty<AppCompatActivity, T> { thisRef, property ->
        thisRef.intent.getParcelableExtra(EXTRA_DESTINATION_PARAMS)!!
    }

fun <T : Parcelable> destinationResult() = object : ReadWriteProperty<AppCompatActivity, T?> {
    private var result: T? = null

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T? {
        return result
    }

    override fun setValue(thisRef: AppCompatActivity, property: KProperty<*>, value: T?) {
        result = value
        val data = Intent().putExtra(EXTRA_DESTINATION_RESULT, result)
        thisRef.setResult(Activity.RESULT_OK, data)
    }
}