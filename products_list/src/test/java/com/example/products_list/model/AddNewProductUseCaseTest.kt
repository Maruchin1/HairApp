package com.example.products_list.model

import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNewProductUseCaseTest {
    private val actions = mockk<AddNewProductUseCase.Actions>()
    private val productDao = mockk<ProductDao>()
    private val addNewProductUseCase by lazy {
        AddNewProductUseCase(actions, productDao)
    }

    @Before
    fun before() {
        coEvery { productDao.insert(*anyVararg()) } returns arrayOf(2)
        coJustRun { actions.openProductDetails(any()) }
    }

    @Test
    fun successfullyAdded() = runBlocking {
        val result = addNewProductUseCase()

        assertThat(result.isRight()).isTrue()
        coVerify {
            productDao.insert(Product())
            actions.openProductDetails(2)
        }
    }
}