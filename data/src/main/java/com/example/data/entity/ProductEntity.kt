package com.example.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.domain.Product

@Entity
internal data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    val productId: Int,

    @Embedded
    val type: Product.Type,

    var name: String,

    var manufacturer: String,

    var photoData: String?
) {

    constructor(product: Product) : this(
        productId = product.id,
        type = product.type,
        name = product.name,
        manufacturer = product.manufacturer,
        photoData = product.photoData
    )
}