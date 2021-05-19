package com.example.room_database.converters

import androidx.room.TypeConverter
import com.example.core.domain.CareStep

internal class CareSchemaStepConverter {

    @TypeConverter
    fun fromCareSchemaStepType(value: CareStep.Type?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toCareSchemaStepType(value: String?): CareStep.Type? {
        return value?.let { CareStep.Type.valueOf(value) }
    }
}