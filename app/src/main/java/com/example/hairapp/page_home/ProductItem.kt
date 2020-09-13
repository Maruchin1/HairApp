package com.example.hairapp.page_home

import android.net.Uri

data class ProductItem(
    val productPhoto: Uri?,
    val productName: String,
    val productManufacturer: String
)