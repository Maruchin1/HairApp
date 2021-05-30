package com.example.corev2.ui

import android.app.Activity
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ImagePickerService {

    suspend fun takeProductPhoto(activity: AppCompatActivity): Uri? = suspendCoroutine {
        val startForResult = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                it.resume(result.data?.data)
            } else {
                it.resume(null)
            }
        }
        ImagePicker.with(activity)
            .crop(x = 1f, y = 1f)
            .compress(maxSize = 1024)
            .maxResultSize(width = 1080, height = 1080)
            .createIntent { intent ->
                startForResult.launch(intent)
            }
//            .start { resultCode, data ->
//                if (resultCode == Activity.RESULT_OK) {
//                    it.resume(data?.data)
//                } else {
//                    it.resume(null)
//                }
//            }
    }


}