package com.example.data.dao

import androidx.room.*
import com.example.data.entity.CareSchemaEntity
import com.example.data.entity.CareSchemaStepEntity
import com.example.data.relations.CareSchemaWithSteps
import com.example.data.room.PatchableDao
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CareSchemaDao {

    @Insert
    suspend fun insert(vararg entity: CareSchemaEntity): Array<Long>

    @Update
    suspend fun update(vararg entity: CareSchemaEntity)

    @Delete
    suspend fun delete(vararg entity: CareSchemaEntity)

    @Query("select * from CareSchemaEntity where careSchemaId = :id")
    fun findById(id: Int): Flow<CareSchemaWithSteps>

    @Query("select * from CareSchemaEntity")
    fun findAll(): Flow<List<CareSchemaWithSteps>>
}