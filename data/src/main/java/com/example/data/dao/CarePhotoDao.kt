package com.example.data.dao

import androidx.room.*
import com.example.data.entity.CarePhotoEntity
import com.example.data.room.PatchableDao
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CarePhotoDao : PatchableDao<CarePhotoEntity> {

    @Query("delete from CarePhotoEntity where data = :data")
    suspend fun deleteByData(data: String)

    @Query("select * from CarePhotoEntity where careId = :careId")
    fun findByCare(careId: Int): Flow<List<CarePhotoEntity>>
}