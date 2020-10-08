package com.example.data.dao

import androidx.room.*
import com.example.data.entity.ApplicationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {

    @Insert
    suspend fun insert(vararg entity: ApplicationEntity)

    @Update
    suspend fun update(vararg entity: ApplicationEntity)

    @Delete
    suspend fun delete(vararg entity: ApplicationEntity)

    @Query("select * from ApplicationEntity where applicationName = :name")
    fun findByName(name: String): Flow<ApplicationEntity>
}