package com.example.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.core.domain.CareStep

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CareSchemaEntity::class,
            parentColumns = ["careSchemaId"],
            childColumns = ["careSchemaId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
internal data class CareSchemaStepEntity(

    @PrimaryKey(autoGenerate = true)
    val careSchemaStepId: Int,

    val type: CareStep.Type,

    var order: Int,

    val careSchemaId: Int
) {
    constructor(careStep: CareStep, careSchemaId: Int) : this(
        careSchemaStepId = 0,
        type = careStep.type,
        order = careStep.order,
        careSchemaId = careSchemaId
    )
}