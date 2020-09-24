package com.example.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.core.domain.Product
import com.example.core.domain.ProductApplication
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockProductRepo @Inject constructor() : ProductRepo {

    private val collection = MutableLiveData(
        mutableListOf(
            Product(
                1,
                name = "Shauma1",
                manufacturer = "Kret sp z.o.o.",
                type = Product.Type(
                    humectants = true,
                    proteins = true
                ),
                applications = mutableSetOf(
                    ProductApplication("Mocny szampon", type = ProductApplication.Type.SHAMPOO),
                    ProductApplication("Olej", type = ProductApplication.Type.OTHER)
                ),
                photoData = null
            ),
            Product(
                2,
                name = "Shauma2",
                manufacturer = "Kret sp z.o.o.",
                type = Product.Type(
                    humectants = true,
                    proteins = true
                ),
                applications = mutableSetOf(
                    ProductApplication("Odżywka", ProductApplication.Type.CONDITIONER)
                ),
                photoData = null
            ),
            Product(
                3,
                name = "Shauma3",
                manufacturer = "Kret sp z.o.o.",
                type = Product.Type(
                    emollients = true
                ),
                applications = mutableSetOf(
                    ProductApplication("Odżywka", ProductApplication.Type.CONDITIONER)
                ),
                photoData = null
            ),
            Product(
                4,
                name = "Shauma4",
                manufacturer = "Kret sp z.o.o.",
                type = Product.Type(
                    emollients = true,
                    proteins = true
                ),
                applications = mutableSetOf(
                    ProductApplication("Odżywka", ProductApplication.Type.CONDITIONER)
                ),
                photoData = null
            ),
            Product(
                5,
                name = "Shauma5",
                manufacturer = "Kret sp z.o.o.",
                type = Product.Type(
                    humectants = true
                ),
                applications = mutableSetOf(),
                photoData = null
            )
        )
    )

    override suspend fun add(product: Product) = collection.updateState {
        val newProduct = product.copy(
            id = it.size + 1
        )
        it.add(newProduct)
    }

    override suspend fun update(product: Product) = collection.updateState {
        it.removeIf { item -> item.id == product.id }
        it.add(product)
    }

    override suspend fun delete(product: Product) = collection.updateState {
        it.remove(product)
    }

    override suspend fun existsByName(productName: String): Boolean {
        return collection.value?.find { it.name == productName } != null
    }

    override fun findById(productId: Int): Flow<Product> {
        return collection.asFlow()
            .map { list ->
                list.find { it.id == productId }
            }.filterNotNull()
    }

    override fun findAll(): Flow<List<Product>> {
        return collection.asFlow()
    }

    override fun findByApplicationType(type: ProductApplication.Type): Flow<List<Product>> {
        return collection.asFlow().map { list ->
            list.filter { product ->
                product.applications.any { productApplication ->
                    productApplication.type == type
                }
            }
        }
    }
}