package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.domain.CareSchema

@Entity
internal data class CareSchemaEntity(

    @PrimaryKey(autoGenerate = true)
    val careSchemaId: Int,

    var name: String
) {
    constructor(careSchema: CareSchema) : this(
        careSchemaId = careSchema.id,
        name = careSchema.name
    )
}