package com.example.hairapp.page_photo_preview

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityPhotoPreviewBinding
import com.example.hairapp.framework.Dialog
import com.example.hairapp.framework.SystemColors
import com.example.hairapp.framework.bindActivity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PhotoPreviewActivity : AppCompatActivity() {

    private val dialog: Dialog by inject()
    private val _photo = MutableLiveData<String>()
    private lateinit var binding: ActivityPhotoPreviewBinding

    val photo: LiveData<String> = _photo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindActivity(R.layout.activity_photo_preview)
        SystemColors(this).allBlack().apply()

        setInputPhoto()
        setupActions()
    }

    private fun setInputPhoto() {
        _photo.value = intent.getStringExtra(INPUT_PHOTO)!!
    }

    private fun setupActions() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_delete -> deletePhoto()
            }
            true
        }
    }

    private fun deletePhoto() = lifecycleScope.launch {
        val confirmed = dialog.confirm(
            context = this@PhotoPreviewActivity,
            title = "Usuń zdjęcie",
            message = "Czy chcesz usunąć wybrane zdjęcie z pielęgnacji? Tej operacji nie będzie można cofnąć."
        )
        if (confirmed) {
            val resultData = Intent().putExtra(RESULT_PHOTO, _photo.value!!)
            setResult(RESULT_CODE_DELETE, resultData)
            finish()
        }
    }

    class Contract : ActivityResultContract<String, String?>() {

        override fun createIntent(context: Context, input: String?): Intent {
            return Intent(context, PhotoPreviewActivity::class.java)
                .putExtra(INPUT_PHOTO, input)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            return intent?.let {
                intent.getStringExtra(RESULT_PHOTO)
            }
        }

    }

    companion object {
        private const val INPUT_PHOTO = "input_photo"
        private const val RESULT_PHOTO = "result"
        private const val RESULT_CODE_DELETE = 10
    }
}