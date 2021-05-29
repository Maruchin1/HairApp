package com.example.corev2.dao

import androidx.room.*
import com.example.corev2.entities.Care
import com.example.corev2.relations.CareWithStepsAndPhotos
import kotlinx.coroutines.flow.Flow

@Dao
interface CareDao {

    @Insert
    suspend fun insert(vararg care: Care): Array<Long>

    @Update
    suspend fun update(vararg care: Care)

    @Delete
    suspend fun delete(vararg care: Care)

    @Transaction
    @Query("SELECT * FROM Care")
    fun getAllCares(): Flow<List<CareWithStepsAndPhotos>>

    @Transaction
    @Query("SELECT * FROM Care WHERE id = :careId")
    fun getById(careId: Long): Flow<CareWithStepsAndPhotos?>
}