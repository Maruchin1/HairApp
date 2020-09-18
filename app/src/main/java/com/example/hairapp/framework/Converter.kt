package com.example.hairapp.framework

import android.net.Uri
import com.example.core.domain.ProductApplication

object Converter {

    @JvmStatic
    fun productApplicationType(data: ProductApplication.Type?): String? {
        return when (data) {
            ProductApplication.Type.CONDITIONER -> "Odżywka"
            ProductApplication.Type.SHAMPOO -> "Szampon"
            ProductApplication.Type.OTHER -> "Inny"
            else -> null
        }
    }

    @JvmStatic
    fun photo(photoData: String?): Uri? {
        return photoData?.let { Uri.parse(it) }
    }
}