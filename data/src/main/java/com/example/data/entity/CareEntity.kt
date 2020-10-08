package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.domain.Care
import java.time.LocalDate

@Entity
internal data class CareEntity(

    @PrimaryKey(autoGenerate = true)
    val careId: Int,

    val type: Care.Type,

    val date: LocalDate
) {
    constructor(care: Care) : this(
        careId = care.id,
        type = care.type,
        date = care.date
    )
}