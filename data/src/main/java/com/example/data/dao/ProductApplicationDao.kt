package com.example.data.dao

import androidx.room.*
import com.example.data.entity.ProductApplicationEntity
import com.example.data.room.PatchableDao
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProductApplicationDao : PatchableDao<ProductApplicationEntity> {

    @Query("select * from ProductApplicationEntity where productId = :productId")
    fun findByProduct(productId: Int): Flow<List<ProductApplicationEntity>>
}