package com.example.photos_gallery

import java.time.LocalDate

sealed class GalleryItem {

    data class DayHeader(val date: LocalDate) : GalleryItem()

    data class Photo(val data: String) : GalleryItem()
}