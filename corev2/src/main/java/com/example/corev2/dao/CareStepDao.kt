package com.example.corev2.dao

import androidx.room.*
import com.example.corev2.entities.CareStep
import kotlinx.coroutines.flow.Flow

@Dao
interface CareStepDao {

    @Insert
    suspend fun insert(vararg careStep: CareStep)

    @Update
    suspend fun update(vararg careStep: CareStep)

    @Delete
    suspend fun delete(vararg careStep: CareStep)

    @Query("SELECT * FROM CareStep WHERE id = :careId")
    fun getByCare(careId: Long): Flow<List<CareStep>>
}