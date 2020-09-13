package com.example.hairapp.model

import android.net.Uri

data class Product(
    var name: String,
    var manufacturer: String,
    val type: ProductType,
    val application: MutableSet<String>,
    var photo: Uri?
)