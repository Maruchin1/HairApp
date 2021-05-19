package com.example.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CareEntity::class,
            parentColumns = ["id"],
            childColumns = ["careId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
internal data class CarePhotoEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Int,

    val data: String,

    val careId: Int
) : BaseEntity {

    constructor(photoData: String, careId: Int) : this(
        id = 0,
        data = photoData,
        careId = careId
    )
}