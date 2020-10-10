package com.example.data.repo

import com.example.core.domain.Product
import com.example.core.gateway.ProductRepo
import com.example.data.dao.ProductDao
import com.example.data.entity.ProductEntity
import com.example.data.room.Mapper
import com.example.data.room.mapList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

internal class RoomProductRepo(
    private val mapper: Mapper,
    private val productDao: ProductDao
) : ProductRepo {

    override suspend fun add(product: Product) {
        productDao.insert(ProductEntity(product)).first().toInt()
    }

    override suspend fun update(product: Product) {
        productDao.update(ProductEntity(product))
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

    override fun findByApplications(applications: List<Product.Application>): Flow<List<Product>> {
        return productDao.findAll()
            .mapList { mapper.toDomain(it) }
            .map { list ->
                list.filter { product ->
                    product.applications.any { it in applications }
                }
            }
    }

}