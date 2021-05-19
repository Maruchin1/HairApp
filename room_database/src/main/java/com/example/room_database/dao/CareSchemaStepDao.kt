package com.example.room_database.dao

import androidx.room.*
import com.example.room_database.entities.CareSchemaStepEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CareSchemaStepDao {

    @Insert
    suspend fun insert(vararg entity: CareSchemaStepEntity)

    @Update
    suspend fun update(vararg entity: CareSchemaStepEntity)

    @Delete
    suspend fun delete(vararg entity: CareSchemaStepEntity)

    @Query("SELECT * FROM CareSchemaStep WHERE careSchemaId = :careSchemaId")
    fun findByCareSchema(careSchemaId: Int): Flow<List<CareSchemaStepEntity>>
}