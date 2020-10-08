package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.data.entity.CarePhotoEntity

@Dao
interface CarePhotoDao {

    @Insert
    suspend fun insert(vararg entity: CarePhotoEntity)

    @Update
    suspend fun update(vararg entity: CarePhotoEntity)

    @Delete
    suspend fun delete(vararg entity: CarePhotoEntity)
}