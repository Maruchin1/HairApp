package com.example.corev2.dao

import androidx.room.*
import com.example.corev2.entities.CareSchema
import com.example.corev2.relations.CareSchemaWithSteps
import kotlinx.coroutines.flow.Flow

@Dao
interface CareSchemaDao {

    @Insert
    suspend fun insert(vararg careSchema: CareSchema): Array<Long>

    @Update
    suspend fun update(vararg careSchema: CareSchema)

    @Delete
    suspend fun delete(vararg careSchema: CareSchema)

    @Query("SELECT * FROM CareSchema")
    fun getAllSchemasWithSteps(): Flow<List<CareSchemaWithSteps>>

    @Query("SELECT * FROM CareSchema WHERE id = :careSchemaId")
    fun getSchemaWithStepsById(careSchemaId: Long): Flow<CareSchemaWithSteps?>
}