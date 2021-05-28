package com.example.corev2.dao

import androidx.room.*
import com.example.corev2.entities.CarePhoto
import kotlinx.coroutines.flow.Flow

@Dao
interface CarePhotoDao {

    @Insert
    suspend fun insert(vararg carePhoto: CarePhoto)

    @Update
    suspend fun update(vararg carePhoto: CarePhoto)

    @Delete
    suspend fun delete(vararg carePhoto: CarePhoto)

    @Query("SELECT * FROM CarePhoto WHERE id = :careId")
    fun getByCare(careId: Long): Flow<List<CarePhoto>>
}