package com.example.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.entity.CareEntity
import com.example.data.entity.CarePhotoEntity
import com.example.data.entity.CareProductEntity

data class CareWithPhotosAndProducts(

    @Embedded
    val care: CareEntity,

    @Relation(
        parentColumn = "careId",
        entityColumn = "careId"
    )
    val photos: List<CarePhotoEntity>,

    @Relation(
        parentColumn = "careId",
        entityColumn = "careId"
    )
    val products: List<CareProductEntity>
)