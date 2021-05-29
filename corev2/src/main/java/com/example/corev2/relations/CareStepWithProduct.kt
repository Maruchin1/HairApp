package com.example.corev2.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.corev2.entities.CareStep
import com.example.corev2.entities.Product

data class CareStepWithProduct(
    @Embedded
    val careStep: CareStep,

    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: Product?
)