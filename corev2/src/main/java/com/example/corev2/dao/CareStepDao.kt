package com.example.corev2.dao

import androidx.room.*
import com.example.corev2.entities.CareStep

@Dao
interface CareStepDao {

    @Insert
    suspend fun insert(vararg careStep: CareStep)

    @Update
    suspend fun update(vararg careStep: CareStep)

    @Delete
    suspend fun delete(vararg careStep: CareStep)
}