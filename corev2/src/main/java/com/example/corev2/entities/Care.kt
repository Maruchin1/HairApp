package com.example.corev2.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class Care(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val schemaName: String = "",
    var date: LocalDateTime,
    var notes: String
)