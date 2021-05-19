package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.domain.Care
import java.time.LocalDate

@Entity
internal data class CareEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Int,

    val schemaName: String,

    val date: LocalDate,

    val notes: String
) : BaseEntity {
    constructor(care: Care) : this(
        id = care.id,
        schemaName = care.schemaName,
        date = care.date,
        notes = care.notes
    )
}