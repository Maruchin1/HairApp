package com.example.hairapp.page_photos_gallery

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hairapp.R
import com.example.hairapp.common.CarePhotoItemController
import com.example.hairapp.framework.bindActivity
import com.example.hairapp.databinding.ActivityPhotosGalleryBinding
import com.example.hairapp.framework.SystemColors
import com.example.hairapp.page_photo_preview.PhotoPreviewActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosGalleryActivity : AppCompatActivity(), CarePhotoItemController {

    private val viewModel: PhotosGalleryViewModel by viewModel()
    private val adapter by lazy { GalleryAdapter(this) }

    private val openPhoto = registerForActivityResult(
        PhotoPreviewActivity.Contract()
    ) { photoToDelete ->
        photoToDelete?.let {
            viewModel.deletePhoto(it)
        }
    }

    private lateinit var binding: ActivityPhotosGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindActivity(R.layout.activity_photos_gallery, viewModel)
        SystemColors(this).allDark().apply()

        binding.photosGrid.adapter = adapter
        binding.photosGrid.layoutManager = makeLayoutManager()

        adapter.setSource(viewModel.galleryItems, this)
    }

    override fun onPhotoSelected(data: String) {
        openPhoto.launch(data)
    }

    private fun makeLayoutManager() = GridLayoutManager(this, 2).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                return when (adapter.getItemType(viewType)) {
                    GalleryAdapter.ItemType.HEADER -> 2
                    GalleryAdapter.ItemType.PHOTO -> 1
                }
            }
        }
    }

    companion object {
        fun makeIntent(context: Context): Intent {
            return Intent(context, PhotosGalleryActivity::class.java)
        }
    }
}