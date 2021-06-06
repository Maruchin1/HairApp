package com.example.navigation.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.inlineactivityresult.ActivityResult
import com.afollestad.inlineactivityresult.coroutines.startActivityAwaitResult
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ImagePickerDestination(
    private val setupPicker: ImagePicker.Builder.() -> ImagePicker.Builder
) {

    suspend fun navigate(originActivity: AppCompatActivity): String? =
        createIntent(originActivity)
            .let { originActivity.startActivityAwaitResult(it) }
            .let { getPhotoDataFromResult(it) }

    private suspend fun createIntent(
        originActivity: AppCompatActivity
    ): Intent = suspendCoroutine {
        ImagePicker.with(originActivity)
            .setupPicker()
            .createIntent { intent -> it.resume(intent) }
    }

    private fun getPhotoDataFromResult(result: ActivityResult): String? =
        if (result.success) result.data.dataString else null
}