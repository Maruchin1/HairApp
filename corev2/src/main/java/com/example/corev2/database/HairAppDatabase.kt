package com.example.corev2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep

@Database(
    entities = [
        CareSchema::class,
        CareSchemaStep::class
    ],
    version = 1
)
@TypeConverters(DatabaseTypesConverter::class)
abstract class HairAppDatabase : RoomDatabase() {

    abstract fun careSchemaDao(): CareSchemaDao

    abstract fun careSchemaStepDao(): CareSchemaStepDao
}