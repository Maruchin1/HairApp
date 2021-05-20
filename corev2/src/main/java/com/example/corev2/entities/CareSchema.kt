package com.example.corev2.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class CareSchema(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    var name: String,
)