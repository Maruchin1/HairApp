package com.example.data.dao

import androidx.room.*
import com.example.core.domain.Application
import com.example.data.entity.ProductEntity
import com.example.data.relations.ProductWithApplications
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProductDao {

    @Insert
    suspend fun insert(vararg entity: ProductEntity): Array<Long>

    @Update
    suspend fun update(vararg entity: ProductEntity)

    @Delete
    suspend fun delete(vararg entity: ProductEntity)

    @Transaction
    @Query("select * from ProductEntity where productId = :id")
    fun findById(id: Int): Flow<ProductWithApplications>

    @Transaction
    @Query("select * from ProductEntity")
    fun findAll(): Flow<List<ProductWithApplications>>
}