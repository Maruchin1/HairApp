package com.example.data.repo

import android.util.Log
import com.example.core.domain.Product
import com.example.core.domain.Application
import com.example.core.gateway.ProductRepo
import com.example.data.dao.ProductApplicationDao
import com.example.data.dao.ProductDao
import com.example.data.entity.ProductApplicationEntity
import com.example.data.entity.ProductEntity
import com.example.data.room.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

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
        productApplicationDao.insert(*productApplicationsEntities.toTypedArray())
    }

    override suspend fun update(product: Product) {
        Log.d("MyDebug", "repo update")
        productDao.update(ProductEntity(product))
        val productApplicationsEntities = product.applications.map {
            ProductApplicationEntity(it, product.id)
        }
        val existingProductApplicationsEntities = productApplicationDao.findByProduct(product.id).first()
        productApplicationsEntities.forEach {
            if (!existingProductApplicationsEntities.contains(it)) {
                productApplicationDao.insert(it)
            }
        }
        existingProductApplicationsEntities.forEach {
            if (!productApplicationsEntities.contains(it)) {
                productApplicationDao.delete(it)
            }
        }
    }

    override suspend fun delete(product: Product) {
        productDao.delete(ProductEntity(product))
    }

    override fun findById(productId: Int): Flow<Product> {
        return productDao.findById(productId)
            .onEach { Log.d("MyDebug", "findById $productId: $it") }
            .map { mapper.toDomain(it) }
    }

    override fun findAll(): Flow<List<Product>> {
        return productDao.findAll().map { list ->
            list.map { mapper.toDomain(it) }
        }
    }

    override fun findByApplicationType(type: Application.Type): Flow<List<Product>> {
        return productDao.findAll().map { list ->
            list.map { mapper.toDomain(it) }
        }.map { list ->
            list.filter { product ->
                product.applications.any { it.type == type }
            }
        }
    }
}