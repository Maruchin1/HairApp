package com.example.data.dao

import androidx.room.*
import com.example.data.entity.ProductApplicationEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProductApplicationDao {

    @Insert
    suspend fun insert(vararg entity: ProductApplicationEntity)

    @Update
    suspend fun update(vararg entity: ProductApplicationEntity)

    @Delete
    suspend fun delete(vararg entity: ProductApplicationEntity)

    @Query("select * from ProductApplicationEntity where productId = :productId")
    fun findByProduct(productId: Int): Flow<List<ProductApplicationEntity>>
}