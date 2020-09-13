package com.example.hairapp.data

import com.example.hairapp.model.Product
import com.example.hairapp.model.ProductType
import com.example.hairapp.repository.ProductRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MockProductRepo @Inject constructor() : ProductRepo {
    private val collection = ConflatedBroadcastChannel(
        mutableSetOf<Product>(
            Product(
                name = "Shauma1",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photo = null
            ),
            Product(
                name = "Shauma2",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photo = null
            ),
            Product(
                name = "Shauma3",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photo = null
            ),
            Product(
                name = "Shauma4",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photo = null
            ),
            Product(
                name = "Shauma5",
                manufacturer = "Kret sp z.o.o.",
                type = ProductType(),
                application = mutableSetOf(),
                photo = null
            )
        )
    )

    override suspend fun addNewProduct(product: Product) {
        val state = collection.value
        state.add(product)
        collection.send(state)
    }

    override suspend fun existsByName(productName: String): Boolean {
        val state = collection.value
        return state.find { it.name == productName } != null
    }

    @FlowPreview
    override fun findAllFlow(): Flow<List<Product>> {
        return collection.asFlow().map { it.toList() }
    }
}