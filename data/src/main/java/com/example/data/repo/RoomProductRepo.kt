package com.example.data.repo

import com.example.core.domain.Product
import com.example.core.domain.Application
import com.example.core.gateway.ProductRepo
import com.example.data.dao.ProductApplicationDao
import com.example.data.dao.ProductDao
import com.example.data.entity.ProductApplicationEntity
import com.example.data.entity.ProductEntity
import com.example.data.room.Mapper
import com.example.data.room.mapList
import com.example.data.room.patch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class RoomProductRepo(
    private val mapper: Mapper,
    private val productDao: ProductDao,
    private val productApplicationDao: ProductApplicationDao
) : ProductRepo {

    override suspend fun add(product: Product) {
        val newProductId = productDao.insert(ProductEntity(product)).first().toInt()
        val productApplicationsEntities = product.applications.map {
            ProductApplicationEntity(it, newProductId)
        }
        productApplicationDao.insert(productApplicationsEntities)
    }

    override suspend fun update(product: Product) {
        productDao.update(ProductEntity(product))
        productApplicationDao.patch(
            newData = product.applications.map { ProductApplicationEntity(it, product.id) },
            existingData = productApplicationDao.findByProduct(product.id).first()
        )
    }

    override suspend fun delete(product: Product) {
        productDao.delete(ProductEntity(product))
    }

    override fun findById(productId: Int): Flow<Product> {
        return productDao.findById(productId)
            .filterNotNull()
            .map { mapper.toDomain(it) }
    }

    override fun findAll(): Flow<List<Product>> {
        return productDao.findAll()
            .mapList { mapper.toDomain(it) }
    }

    override fun findByApplicationType(type: Application.Type): Flow<List<Product>> {
        return productDao.findAll()
            .mapList { mapper.toDomain(it) }
            .map { list ->
                list.filter { product ->
                    product.applications.any { it.type == type }
                }
            }
    }
}