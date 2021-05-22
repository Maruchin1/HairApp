package com.example.corev2.room

import androidx.room.TypeConverter
import com.example.corev2.entities.ProductType

internal class DatabaseTypesConverter {

    @TypeConverter
    fun fromProductType(value: ProductType?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toProductType(value: String?): ProductType? {
        return value?.let { ProductType.valueOf(it) }
    }
}