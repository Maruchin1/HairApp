package com.example.hairapp.framework

import android.net.Uri
import com.example.core.domain.Product

val Product.photoUri: Uri?
    get() = photoData?.let { Uri.parse(it) }