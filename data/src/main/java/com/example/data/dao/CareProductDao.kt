package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.data.entity.CareProductEntity

@Dao
internal interface CareProductDao {

    @Insert
    suspend fun insert(vararg entity: CareProductEntity)

    @Update
    suspend fun update(vararg entity: CareProductEntity)

    @Delete
    suspend fun delete(vararg entity: CareProductEntity)
}