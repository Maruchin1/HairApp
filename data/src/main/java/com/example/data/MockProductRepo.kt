package com.example.data

import com.example.core.domain.Product
import com.example.core.domain.ProductApplication
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
class MockProductRepo @Inject constructor() : ProductRepo {

    private val collection = ConflatedBroadcastChannel(
        mutableListOf<Product>(
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

    override suspend fun add(product: Product) {
        val state = collection.value
        val newProduct = product.copy(
            id = state.size + 1
        )
        state.add(newProduct)
        collection.send(state)
    }

    override suspend fun update(product: Product) {
        val state = collection.value
        state.removeIf { it.id == product.id }
        state.add(product)
        collection.send(state)
    }

    override suspend fun delete(product: Product) {
        val state = collection.value
        state.remove(product)
        collection.send(state)
    }

    override suspend fun existsByName(productName: String): Boolean {
        return collection.value.find { it.name == productName } != null
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