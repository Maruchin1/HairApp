package com.example.corev2.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.Product

@Database(
    entities = [
        CareSchema::class,
        CareSchemaStep::class,
        Product::class
    ],
    version = 2
)
@TypeConverters(DatabaseTypesConverter::class)
abstract class HairAppDatabase : RoomDatabase() {

    abstract fun careSchemaDao(): CareSchemaDao

    abstract fun careSchemaStepDao(): CareSchemaStepDao

    abstract fun productDao(): ProductDao
}