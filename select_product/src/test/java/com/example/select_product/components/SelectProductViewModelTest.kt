package com.example.select_product.components

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.example.testing.rules.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SelectProductViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val productDao = mockk<ProductDao>()
    private val viewModel by lazy {
        SelectProductViewModel(productDao)
    }

    private val productsFromDb = listOf(
        Product(
            id = 1,
            name = "Super shampoo",
            applications = setOf(
                Product.Application.STRONG_SHAMPOO
            )
        ),
        Product(
            id = 2,
            name = "Super conditioner",
            applications = setOf(
                Product.Application.CONDITIONER
            )
        ),
        Product(
            id = 3,
            name = "Other super shampoo",
            applications = setOf(
                Product.Application.MILD_SHAMPOO
            )
        )
    )

    @Before
    fun before() {
        every { productDao.getAll() } returns flowOf(productsFromDb)
    }

    @Test
    fun emitProductsOfSelectedType_InAlphabeticalOrder() = runBlocking {
        val selectedType = Product.Type.SHAMPOO

        viewModel.onProductTypeSelected(selectedType)
        val result = viewModel.productsOfSelectedType.asFlow().firstOrNull()

        assertThat(result).containsExactly(
            productsFromDb[2],
            productsFromDb[0]
        ).inOrder()
    }
}