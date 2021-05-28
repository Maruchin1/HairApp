package com.example.corev2.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Care::class,
            parentColumns = ["id"],
            childColumns = ["careId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class CareStep(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val productType: Product.Type,
    var order: Int,
    var productId: Long?,
    val careId: Long
)