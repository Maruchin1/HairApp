package com.example.corev2.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CareSchema::class,
            parentColumns = ["id"],
            childColumns = ["careSchemaId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CareSchemaStep(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val productType: Product.Type,

    var order: Int,

    val careSchemaId: Long
) {

    fun toCareStep() = CareStep(-1, productType, order, null, -1)
}