package com.example.navigation.intent_builders

import android.app.Activity
import android.content.Intent
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class CaptureCarePhotoIntentBuilder : IntentBuilder {

    override suspend fun buildIntent(originActivity: Activity): Intent = suspendCoroutine {
        ImagePicker.with(originActivity)
            .crop(x = 3f, y = 4f)
            .compress(maxSize = 1024)
            .maxResultSize(width = 1080, height = 1440)
            .createIntent { intent -> it.resume(intent) }
    }
}