package com.example.corev2.room_database

import androidx.room.TypeConverter
import com.example.corev2.entities.Product
import java.time.LocalDate

internal class DatabaseTypesConverter {

    @TypeConverter
    fun fromProductType(value: Product.Type?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toProductType(value: String?): Product.Type? {
        return value?.let { Product.Type.valueOf(it) }
    }

    @TypeConverter
    fun fromProductApplication(value: Product.Application?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toProductApplication(value: String?): Product.Application? {
        return value?.let { Product.Application.valueOf(it) }
    }

    @TypeConverter
    fun fromApplicationsSet(value: Set<Product.Application>?): String? {
        return value?.joinToString(",")
    }

    @TypeConverter
    fun toApplicationSet(value: String?): Set<Product.Application>? {
        if (value == null) {
            return null
        }
        if (value.isEmpty() || value.isBlank()) {
            return emptySet()
        }
        return value
            .split(",")
            .map { Product.Application.valueOf(it) }
            .toSet()
    }

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }
}