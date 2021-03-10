package com.example.hairapp.page_photos_gallery

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hairapp.R
import com.example.hairapp.common.CarePhotoItemController
import com.example.hairapp.databinding.ActivityPhotosGalleryBinding
import com.example.hairapp.framework.bindActivity
import com.example.hairapp.common.PhotoPreviewDialog
import com.example.hairapp.framework.SystemColors
import kotlinx.android.synthetic.main.activity_photos_gallery.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosGalleryActivity : AppCompatActivity(), CarePhotoItemController {

    private val viewModel: PhotosGalleryViewModel by viewModel()
    private val adapter by lazy { GalleryAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindActivity<ActivityPhotosGalleryBinding>(R.layout.activity_photos_gallery, viewModel)
        SystemColors(this).allDark()

        gallery_photos_adapter.adapter = adapter
        gallery_photos_adapter.layoutManager = makeLayoutManager()

        adapter.setSource(viewModel.galleryItems, this)
    }

    override fun onPhotoSelected(data: String) {
        PhotoPreviewDialog(
            photo = data,
            onPhotoDelete = { viewModel.deletePhoto(data) }
        ).show(supportFragmentManager, "PhotoPreview")
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