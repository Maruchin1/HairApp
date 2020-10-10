package com.example.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.core.domain.CareStep

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
internal data class CareStepEntity(

    @PrimaryKey(autoGenerate = true)
    val careProductId: Int,

    val type: CareStep.Type,

    var order: Int,

    var productId: Int?,

    val careId: Int
) {
    constructor(careStep: CareStep, careId: Int) : this(
        careProductId = 0,
        type = careStep.type,
        order = careStep.order,
        productId = careStep.product?.id,
        careId = careId
    )
}