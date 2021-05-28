package com.example.corev2.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Care(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val schemaName: String = "",
    var date: LocalDate,
    var notes: String
)