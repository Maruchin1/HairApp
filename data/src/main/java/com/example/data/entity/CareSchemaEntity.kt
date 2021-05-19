package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.domain.CareSchema

@Entity
internal data class CareSchemaEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Int,

    var name: String
) : BaseEntity {
    constructor(careSchema: CareSchema) : this(
        id = careSchema.id,
        name = careSchema.name
    )
}