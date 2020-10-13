package com.example.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.entity.CareSchemaStepEntity
import com.example.data.room.PatchableDao
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CareSchemaStepDao : PatchableDao<CareSchemaStepEntity> {

    @Query("select * from CareSchemaStepEntity where careSchemaId = :careSchemaId")
    fun findByCareSchema(careSchemaId: Int): Flow<List<CareSchemaStepEntity>>
}