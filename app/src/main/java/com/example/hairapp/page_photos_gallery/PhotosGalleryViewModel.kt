package com.example.hairapp.page_photos_gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.base.invoke
import com.example.core.domain.DayPhotos
import com.example.core.use_case.DeleteCarePhoto
import com.example.core.use_case.ShowPhotosReview
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PhotosGalleryViewModel(
    private val showPhotosReview: ShowPhotosReview,
    private val deleteCarePhoto: DeleteCarePhoto
) : ViewModel() {

    val galleryItems: LiveData<List<GalleryItem>> = showPhotosReview()
        .map { it.toGalleryItems() }
        .asLiveData()

    fun deletePhoto(photo: String) = viewModelScope.launch {
        val input = DeleteCarePhoto.Input(photo)
        deleteCarePhoto(input)
    }

    private fun List<DayPhotos>.toGalleryItems(): List<GalleryItem> {
        val list = mutableListOf<GalleryItem>()
        forEach {
            val header = GalleryItem.DayHeader(it.date)
            val photos = it.photos.map { GalleryItem.Photo(it) }
            list.add(header)
            list.addAll(photos)
        }
        return list
    }

}