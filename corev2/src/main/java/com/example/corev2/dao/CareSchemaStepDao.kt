package com.example.corev2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.corev2.entities.CareSchemaStep

@Dao
interface CareSchemaStepDao {

    @Insert
    suspend fun insert(vararg careSchemaStep: CareSchemaStep)

    @Update
    suspend fun update(vararg careSchemaStep: CareSchemaStep)

    @Delete
    suspend fun delete(vararg careSchemaStep: CareSchemaStep)
}