package com.example.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CareSchemaEntity::class,
            parentColumns = ["id"],
            childColumns = ["careSchemaId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
internal data class CareSchemaStepEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Int,

    val type: CareStep.Type,

    var order: Int,

    val careSchemaId: Int
) : BaseEntity {
    constructor(careStep: CareSchemaStep, careSchemaId: Int) : this(
        id = 0,
        type = careStep.type,
        order = careStep.order,
        careSchemaId = careSchemaId
    )
}