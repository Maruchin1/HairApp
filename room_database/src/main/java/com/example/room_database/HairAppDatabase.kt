package com.example.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.room_database.converters.CareSchemaStepConverter
import com.example.room_database.dao.CareSchemaDao
import com.example.room_database.dao.CareSchemaStepDao
import com.example.room_database.entities.CareSchemaEntity
import com.example.room_database.entities.CareSchemaStepEntity

@Database(
    entities = [
        CareSchemaEntity::class,
        CareSchemaStepEntity::class
    ],
    version = 1
)
@TypeConverters(
    CareSchemaStepConverter::class
)
internal abstract class HairAppDatabase : RoomDatabase() {
    abstract fun careSchemaDao(): CareSchemaDao
    abstract fun careSchemaStepDao(): CareSchemaStepDao
}