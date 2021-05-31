package com.example.corev2.navigation

import androidx.appcompat.app.AppCompatActivity
import com.afollestad.inlineactivityresult.coroutines.startActivityAwaitResult
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CaptureCarePhotoDestination {

    suspend fun navigate(activity: AppCompatActivity): String? = suspendCoroutine {
        ImagePicker.with(activity)
            .crop(x = 3f, y = 4f)
            .compress(maxSize = 1024)
            .maxResultSize(width = 1080, height = 1440)
            .createIntent { intent ->
                GlobalScope.launch {
                    val activityResult = activity.startActivityAwaitResult(intent)
                    if (activityResult.success) {
                        it.resume(activityResult.data.data?.toString())
                    } else {
                        it.resume(null)
                    }
                }
            }
    }
}