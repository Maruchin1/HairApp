package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.dao.*
import com.example.data.entity.*

@Database(
    entities = [
        ApplicationEntity::class,
        CareEntity::class,
        CarePhotoEntity::class,
        CareStepEntity::class,
        ProductApplicationEntity::class,
        ProductEntity::class
    ],
    version = 1
)
@TypeConverters(RoomConverter::class)
internal abstract class RoomDatabase : RoomDatabase() {

    abstract fun applicationDao(): ApplicationDao

    abstract fun careDao(): CareDao

    abstract fun carePhotoDao(): CarePhotoDao

    abstract fun careProductDao(): CareStepDao

    abstract fun productApplicationDao(): ProductApplicationDao

    abstract fun productDao(): ProductDao
}