package com.example.room_database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.room_database.entities.CareSchemaEntity

@Dao
internal interface CareSchemaDao {

    @Insert
    suspend fun insert(vararg entity: CareSchemaEntity): Array<Long>

    @Insert
    suspend fun update(vararg entity: CareSchemaEntity)
}