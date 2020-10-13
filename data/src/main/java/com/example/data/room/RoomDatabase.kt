package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.dao.*
import com.example.data.entity.*

@Database(
    entities = [
        CareEntity::class,
        CarePhotoEntity::class,
        CareSchemaEntity::class,
        CareSchemaStepEntity::class,
        CareStepEntity::class,
        ProductEntity::class
    ],
    version = 1
)
@TypeConverters(RoomConverter::class)
internal abstract class RoomDatabase : RoomDatabase() {

    abstract fun careDao(): CareDao

    abstract fun carePhotoDao(): CarePhotoDao

    abstract fun careSchemaDao(): CareSchemaDao

    abstract fun careSchemaStepDao(): CareSchemaStepDao

    abstract fun careProductDao(): CareStepDao

    abstract fun productDao(): ProductDao
}