package com.example.corev2

import androidx.room.TypeConverter
import com.example.corev2.entities.CareSchemaStep

internal class DatabaseTypesConverter {

    @TypeConverter
    fun fromCareSchemaStepType(value: CareSchemaStep.Type?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toCareSchemaStepType(value: String?): CareSchemaStep.Type? {
        return value?.let { CareSchemaStep.Type.valueOf(it) }
    }
}