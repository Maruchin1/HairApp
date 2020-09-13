package com.example.data

import com.example.core.domain.Product
import com.example.core.domain.ProductType
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
class MockProductRepo @Inject constructor() : ProductRepo {

    private val collection = ConflatedBroadcastChannel(
        mutableSetOf<Product>(
            Product(
                name = "Shauma1",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photoData = null
            ),
            Product(
                name = "Shauma2",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photoData = null
            ),
            Product(
                name = "Shauma3",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photoData = null
            ),
            Product(
                name = "Shauma4",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photoData = null
            ),
            Product(
                name = "Shauma5",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photoData = null
            )
        )
    )

    override suspend fun addNewProduct(product: Product) {
        val state = collection.value
        state.add(product)
        collection.send(state)
    }

    override suspend fun existsByName(productName: String): Boolean {
        return collection.value.find { it.name == productName } != null
    }

    override fun findByNameFlow(productName: String): Flow<Product> {
        return collection.asFlow().map { collection ->
            collection.find { it.name == productName }!!
        }
    }

    override fun findAllFlow(): Flow<List<Product>> {
        return collection.asFlow().map { it.toList() }
    }
}