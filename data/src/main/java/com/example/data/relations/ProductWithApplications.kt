package com.example.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.data.entity.ApplicationEntity
import com.example.data.entity.ProductEntity
import com.example.data.entity.ProductApplicationEntity

internal data class ProductWithApplications(

    @Embedded
    val product: ProductEntity,

    @Relation(
        parentColumn = "productId",
        entityColumn = "productId",
        associateBy = Junction(ProductApplicationEntity::class)
    )
    val applications: List<ProductApplicationEntity>
)