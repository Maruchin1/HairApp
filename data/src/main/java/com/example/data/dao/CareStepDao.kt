package com.example.data.dao

import androidx.room.*
import com.example.data.entity.CareStepEntity
import com.example.data.room.PatchableDao
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CareStepDao : PatchableDao<CareStepEntity> {

    @Query("select * from CareStepEntity where careId = :careId")
    fun findByCare(careId: Int): Flow<List<CareStepEntity>>
}