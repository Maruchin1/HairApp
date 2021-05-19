package com.example.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.core.domain.CareStep

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CareEntity::class,
            parentColumns = ["id"],
            childColumns = ["careId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
internal data class CareStepEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Int,

    val type: CareStep.Type,

    var order: Int,

    var productId: Int?,

    val careId: Int
) : BaseEntity {
    constructor(careStep: CareStep, careId: Int) : this(
        id = 0,
        type = careStep.type,
        order = careStep.order,
        productId = careStep.product?.id,
        careId = careId
    )
}