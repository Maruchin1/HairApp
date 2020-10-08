package com.example.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.core.domain.Application

@Entity(
    primaryKeys = ["productId", "applicationName"],
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
internal data class ProductApplicationEntity(

    val productId: Int,

    val applicationName: String
) {
    constructor(application: Application, productId: Int) : this(
        productId = productId,
        applicationName = application.name
    )
}