package com.example.corev2.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.corev2.entities.Care
import com.example.corev2.entities.CarePhoto
import com.example.corev2.entities.CareStep

data class CareWithStepsAndPhotos(
    @Embedded
    val care: Care,

    @Relation(
        parentColumn = "id",
        entityColumn = "careId",
        entity = CareStep::class
    )
    val steps: List<CareStepWithProduct>,

    @Relation(
        parentColumn = "id",
        entityColumn = "careId"
    )
    val photos: List<CarePhoto>
)