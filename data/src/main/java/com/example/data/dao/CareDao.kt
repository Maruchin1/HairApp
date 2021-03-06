package com.example.data.dao

import androidx.room.*
import com.example.data.entity.CareEntity
import com.example.data.relations.CareWithPhotosAndProducts
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CareDao {

    @Insert
    suspend fun insert(vararg entity: CareEntity): Array<Long>

    @Update
    suspend fun update(vararg entity: CareEntity)

    @Delete
    suspend fun delete(vararg entity: CareEntity)

    @Transaction
    @Query("select * from CareEntity where careId = :id")
    fun findById(id: Int): Flow<CareWithPhotosAndProducts>

    @Transaction
    @Query("select * from CareEntity")
    fun findAll(): Flow<List<CareWithPhotosAndProducts>>

    @Transaction
    @Query("select * from CareEntity limit :numOfCares")
    fun findLastN(numOfCares: Int): Flow<List<CareWithPhotosAndProducts>>

}