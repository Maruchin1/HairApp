package com.example.corev2.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.corev2.dao.*
import com.example.corev2.entities.*

@Database(
    entities = [
        Care::class,
        CareSchema::class,
        CareSchemaStep::class,
        CarePhoto::class,
        CareStep::class,
        Product::class
    ],
    version = 2
)
@TypeConverters(DatabaseTypesConverter::class)
abstract class HairAppDatabase : RoomDatabase() {

    abstract fun careSchemaDao(): CareSchemaDao

    abstract fun careSchemaStepDao(): CareSchemaStepDao

    abstract fun productDao(): ProductDao

    abstract fun careDao(): CareDao

    abstract fun carePhotoDao(): CarePhotoDao

    abstract fun careStepDao(): CareStepDao
}