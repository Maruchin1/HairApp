package com.example.data.room

import android.util.Log
import androidx.room.TypeConverter
import com.example.core.domain.CareStep
import com.example.core.domain.Product
import java.time.LocalDate

internal class RoomConverter {

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(value) }
    }

    @TypeConverter
    fun fromProductApplications(value: Set<Product.Application>?): String? {
        Log.d("RoomConverter", "applications: $value")
        return value?.joinToString(",").also { Log.d("RoomConverter", "converted: $it") }
    }

    @TypeConverter
    fun toProductApplications(value: String?): Set<Product.Application>? {
        if (value?.isEmpty() == true || value?.isBlank() == true) {
            return emptySet()
        }
        return value
            ?.split(",")
            ?.map { Product.Application.valueOf(it) }
            ?.toSet() ?: emptySet()
    }

    @TypeConverter
    fun fromCareStepType(value: CareStep.Type?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toCareStepType(value: String?): CareStep.Type? {
        return value?.let { CareStep.Type.valueOf(it) }
    }

}
