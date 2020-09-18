package com.example.data

import android.util.Log
import com.example.core.domain.Product
import com.example.core.domain.ProductType
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
                type = ProductType(
                    humectants = true,
                    proteins = true
                ),
                application = mutableSetOf("Mocny szampon", "Inny"),
                photoData = null
            ),
            Product(
                2,
                name = "Shauma2",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photoData = null
            ),
            Product(
                3,
                name = "Shauma3",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photoData = null
            ),
            Product(
                4,
                name = "Shauma4",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photoData = null
            ),
            Product(
                5,
                name = "Shauma5",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photoData = null
            )
        )
    )

    override suspend fun add(product: Product) {
        val state = collection.value
        state.add(product)
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

    override fun findByName(productName: String): Flow<Product> {
        return collection.asFlow()
            .map { collection ->
                collection.find { it.name == productName }
            }.filterNotNull()
    }

    override fun findAll(): Flow<List<Product>> {
        return collection.asFlow()
    }
}