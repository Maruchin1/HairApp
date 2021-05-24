package com.example.products_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.example.testing.rules.CoroutinesTestRule
import com.google.common.truth.Truth.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class ProductsListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val productDao: ProductDao = mockk()
    private val viewModel by lazy {
        ProductsListViewModel(productDao)
    }

    @Test
    fun products_EmitAllProductsFromDb_SortedAlphabetically() = runBlocking {
        val productsFromDb = listOf(
            Product(id = 1, name = "Super szampon"),
            Product(id = 2, name = "Ale odżywka"),
            Product(id = 3, name = "Turbo olej")
        )
        every { productDao.getAll() } returns flowOf(productsFromDb)

        val result = viewModel.products.asFlow().firstOrNull()

        assertThat(result).containsExactly(
            productsFromDb[1],
            productsFromDb[0],
            productsFromDb[2]
        ).inOrder()
    }
}