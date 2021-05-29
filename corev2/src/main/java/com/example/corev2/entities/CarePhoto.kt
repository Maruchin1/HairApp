package com.example.corev2.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

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
data class CarePhoto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val data: String,
    val careId: Long
)