package com.example.products_list

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.example.navigation.ProductDetailsDestination
import com.example.navigation.ProductDetailsParams
import com.example.products_list.model.AddNewProductUseCase
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
    val coroutinesTestRule = CoroutinesTestRule()

    private val productDao = mockk<ProductDao>()
    private val actions = mockk<ProductsListViewModel.Actions>()
    private val addNewProductUseCase = mockk<AddNewProductUseCase>()
    private val viewModel by lazy {
        ProductsListViewModel(productDao, actions, addNewProductUseCase)
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

        val result = viewModel.state.value.products

        assertThat(result).containsExactly(
            productsFromDb[1],
            productsFromDb[0],
            productsFromDb[2]
        ).inOrder()
    }

    @Test
    fun noProducts_EmitTrue_WhenNoProductsInDb() = runBlocking {
        val result = viewModel.state.value.noProducts

        assertThat(result).isTrue()
    }

    @Test
    fun noProducts_EmitFalse_WhenProductsAvailableInDb() = runBlocking {
        val productsFromDb = listOf(
            Product(id = 1, name = "Super szampon")
        )
        every { productDao.getAll() } returns flowOf(productsFromDb)

        val result = viewModel.state.value.noProducts

        assertThat(result).isFalse()
    }

    @Test
    fun onAddProductClick_InvokeAddNewProductUseCase() {
        coEvery { addNewProductUseCase() } returns Either.Right(Unit)

        viewModel.onAddProductClicked()

        coVerify {
            addNewProductUseCase()
        }
    }
}