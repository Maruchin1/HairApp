package com.example.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CareEntity::class,
            parentColumns = ["careId"],
            childColumns = ["careId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
internal data class CarePhotoEntity(

    @PrimaryKey(autoGenerate = true)
    val photoId: Int,

    val data: String,

    val careId: Int
) {

    constructor(photoData: String, careId: Int) : this(
        photoId = 0,
        data = photoData,
        careId = careId
    )
}