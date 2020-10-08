package com.example.data.room

import androidx.room.TypeConverter
import com.example.core.domain.Application
import com.example.core.domain.Care
import java.time.LocalDate

internal class RoomConverter {

    @TypeConverter
    fun fromApplicationType(value: Application.Type?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toApplicationType(value: String?): Application.Type? {
        return value?.let { Application.Type.valueOf(it) }
    }

    @TypeConverter
    fun fromCareType(value: Care.Type?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toCareType(value: String?): Care.Type? {
        return value?.let { Care.Type.valueOf(it) }
    }

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(value) }
    }

}
