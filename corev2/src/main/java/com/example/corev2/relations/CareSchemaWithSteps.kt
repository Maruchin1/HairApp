package com.example.corev2.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep

data class CareSchemaWithSteps(

    @Embedded
    val careSchema: CareSchema,

    @Relation(
        parentColumn = "id",
        entityColumn = "careSchemaId"
    )
    val steps: List<CareSchemaStep>
)