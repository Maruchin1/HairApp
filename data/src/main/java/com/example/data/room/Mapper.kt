package com.example.data.room

import com.example.core.domain.*
import com.example.data.dao.ProductDao
import com.example.data.entity.ProductEntity
import com.example.data.relations.CareSchemaWithSteps
import com.example.data.relations.CareWithPhotosAndProducts
import kotlinx.coroutines.flow.firstOrNull

internal class Mapper(
    private val productDao: ProductDao
) {

    suspend fun toDomain(entity: CareWithPhotosAndProducts) = Care(
        id = entity.care.id,
        schemaName = entity.care.schemaName,
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
        }.sortedBy { it.order },
        notes = entity.care.notes
    )

    fun toDomain(entity: ProductEntity) = Product(
        id = entity.id,
        name = entity.name,
        composition = entity.composition,
        manufacturer = entity.manufacturer,
        applications = entity.applications,
        photoData = entity.photoData
    )

    fun toDomain(entity: CareSchemaWithSteps) = CareSchema(
        id = entity.careSchema.id,
        name = entity.careSchema.name,
        steps = entity.steps.map { careSchemaStepEntity ->
            CareSchemaStep(
                id = careSchemaStepEntity.id,
                type = careSchemaStepEntity.type,
                order = careSchemaStepEntity.order,
            )
        }.sortedBy { it.order }
    )
}