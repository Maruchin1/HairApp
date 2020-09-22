package com.example.hairapp.framework

import android.net.Uri
import com.example.core.domain.ProductApplication
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

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

    @JvmStatic
    fun dayOfMonth(date: LocalDate?): String? {
        val formatter = DateTimeFormatter.ofPattern("dd")
        return date?.format(formatter)
    }

    @JvmStatic
    fun shortMonth(date: LocalDate?): String? {
        return date?.month?.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    }
}