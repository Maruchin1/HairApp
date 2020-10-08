package com.example.data.room

import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import com.example.core.domain.Product
import com.example.core.domain.Application
import com.example.data.dao.ApplicationDao
import com.example.data.dao.CareDao
import com.example.data.dao.ProductDao
import com.example.data.entity.ApplicationEntity
import com.example.data.entity.ProductApplicationEntity
import com.example.data.relations.CareWithPhotosAndProducts
import com.example.data.relations.ProductWithApplications
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

internal class Mapper(
    private val applicationDao: ApplicationDao,
    private val careDao: CareDao,
    private val productDao: ProductDao
) {

    suspend fun toDomain(entity: CareWithPhotosAndProducts) = Care(
        id = entity.care.careId,
        type = entity.care.type,
        date = entity.care.date,
        photos = entity.photos.map { it.data },
        steps = entity.products.map { careProductEntity ->
            CareProduct(
                specificApplicationType = careProductEntity.specificApplicationType,
                product = careProductEntity.productId?.let {
                    productDao.findById(it).firstOrNull()
                }?.let {
                    toDomain(it)
                }
            )
        }
    )

    suspend fun toDomain(entity: ProductWithApplications) = Product(
        id = entity.product.productId,
        name = entity.product.name,
        type = entity.product.type,
        manufacturer = entity.product.manufacturer,
        applications = entity.applications.map { toDomain(it) }.toSet(),
        photoData = entity.product.photoData
    )

    suspend fun toDomain(entity: ProductApplicationEntity): Application {
        val applicationEntity = applicationDao.findByName(entity.applicationName).first()
        return toDomain(applicationEntity)
    }

    suspend fun toDomain(entity: ApplicationEntity): Application {
        return Application(
            name = entity.applicationName,
            type = entity.type
        )
    }
}