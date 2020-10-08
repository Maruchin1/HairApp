package com.example.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.core.domain.Application
import com.example.core.domain.CareProduct

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CareEntity::class,
            parentColumns = ["careId"],
            childColumns = ["careId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
internal data class CareProductEntity(

    @PrimaryKey(autoGenerate = true)
    val careProductId: Int,

    val specificApplicationType: Application.Type?,

    var productId: Int?,

    val careId: Int
) {
    constructor(careProduct: CareProduct, careId: Int) : this(
        careProductId = 0,
        specificApplicationType = careProduct.specificApplicationType,
        productId = careProduct.product?.id,
        careId = careId
    )
}