package com.example.data.room

import com.example.core.domain.Care
import com.example.core.domain.CareStep
import com.example.core.domain.Product
import com.example.data.dao.CareDao
import com.example.data.dao.ProductDao
import com.example.data.entity.ProductEntity
import com.example.data.relations.CareWithPhotosAndProducts
import kotlinx.coroutines.flow.firstOrNull

internal class Mapper(
    private val careDao: CareDao,
    private val productDao: ProductDao
) {

    suspend fun toDomain(entity: CareWithPhotosAndProducts) = Care(
        id = entity.care.careId,
        date = entity.care.date,
        photos = entity.photos.map { it.data },
        steps = entity.steps.map { careStepEntity ->
            CareStep(
                type = careStepEntity.type,
                order = careStepEntity.order,
                product = careStepEntity.productId?.let {
                    productDao.findById(it).firstOrNull()
                }?.let {
                    toDomain(it)
                }
            )
        }.sortedBy { it.order }
    )

    fun toDomain(entity: ProductEntity) = Product(
        id = entity.productId,
        name = entity.name,
        composition = entity.composition,
        manufacturer = entity.manufacturer,
        applications = entity.applications,
        photoData = entity.photoData
    )
}