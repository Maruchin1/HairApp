package com.example.corev2.dao

import androidx.room.*
import com.example.corev2.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(vararg product: Product): Array<Long>

    @Update
    suspend fun update(vararg product: Product)

    @Delete
    suspend fun delete(vararg product: Product)

    @Query("SELECT * FROM Product")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE id = :productId")
    fun getById(productId: Long): Flow<Product?>
}