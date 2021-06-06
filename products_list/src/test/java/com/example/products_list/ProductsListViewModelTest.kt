package com.example.products_list

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.example.navigation.ProductDetailsDestination
import com.example.navigation.ProductDetailsParams
import com.example.products_list.model.ProductsListViewModel
import com.example.testing.rules.CoroutinesTestRule
import com.google.common.truth.Truth.*
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductsListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val productDao: ProductDao = mockk()
    private val productFormDestination = mockk<ProductDetailsDestination>()
    private val viewModel by lazy {
        ProductsListViewModel(productDao, productFormDestination)
    }

    @Before
    fun before() {
        every { productDao.getAll() } returns flowOf(listOf())
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

    @Test
    fun noProducts_EmitTrue_WhenNoProductsInDb() = runBlocking {
        val result = viewModel.noProducts.asFlow().firstOrNull()

        assertThat(result).isTrue()
    }

    @Test
    fun noProducts_EmitFalse_WhenProductsAvailableInDb() = runBlocking {
        val productsFromDb = listOf(
            Product(id = 1, name = "Super szampon")
        )
        every { productDao.getAll() } returns flowOf(productsFromDb)

        val result = viewModel.noProducts.asFlow().firstOrNull()

        assertThat(result).isFalse()
    }

    @Test
    fun onAddProductClick_NavigateToProductForm() {
        coJustRun { productFormDestination.navigate(any(), any()) }
        val activity: Activity = mockk()

        viewModel.onAddProductClicked(activity)

        coVerify {
            productFormDestination.navigate(
                originActivity = activity,
                params = ProductDetailsParams(-1)
            )
        }
    }
}