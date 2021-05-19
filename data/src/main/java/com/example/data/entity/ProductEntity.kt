package com.example.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.domain.Product

@Entity
internal data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Int,

    @Embedded
    val composition: Product.Composition,

    var name: String,

    var manufacturer: String,

    var applications: Set<Product.Application>,

    var photoData: String?
) : BaseEntity {

    constructor(product: Product) : this(
        id = product.id,
        composition = product.composition,
        name = product.name,
        manufacturer = product.manufacturer,
        applications = product.applications,
        photoData = product.photoData
    )
}