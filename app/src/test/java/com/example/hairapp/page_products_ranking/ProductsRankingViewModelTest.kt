package com.example.hairapp.page_products_ranking

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.core.domain.Care
import com.example.core.domain.CareStep
import com.example.core.domain.CaresLimit
import com.example.core.domain.Product
import com.example.core.gateway.AppPreferences
import com.example.core.gateway.CareRepo
import com.example.hairapp.CoroutineTestRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class ProductsRankingViewModelTest {
    private val careRepo: CareRepo = mockk()
    private val appPreferences: AppPreferences = mockk()

    private fun makeViewModel() = ProductsRankingViewModel(careRepo, appPreferences)

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun makeCorrectProductsRanking() = runBlocking {
        val allCares = listOf(
            Care(
                id = 1,
                steps = listOf(
                    CareStep(
                        type = CareStep.Type.CONDITIONER,
                        order = 1,
                        product = Product(id = 1, name = "Odżywka 1")
                    ),
                    CareStep(
                        type = CareStep.Type.SHAMPOO,
                        order = 2,
                        product = Product(id = 2, name = "Szampon 1")
                    ),
                    CareStep(
                        type = CareStep.Type.CONDITIONER,
                        order = 1,
                        product = Product(id = 1, name = "Odżywka 1")
                    )
                )
            ),
            Care(
                id = 2,
                steps = listOf(
                    CareStep(
                        type = CareStep.Type.CONDITIONER,
                        order = 4,
                        product = Product(id = 3, name = "Odżywka 2")
                    ),
                    CareStep(
                        type = CareStep.Type.SHAMPOO,
                        order = 2,
                        product = Product(id = 2, name = "Szampon 1")
                    ),
                    CareStep(
                        type = CareStep.Type.CONDITIONER,
                        order = 1,
                        product = Product(id = 1, name = "Odżywka 1")
                    ),
                )
            )
        )
        every { careRepo.findAll() } returns flowOf(allCares)
        every { appPreferences.getProductsRankingCaresLimit() } returns flowOf(CaresLimit.ALL)

        val viewModel = makeViewModel()
        val productsRanking = viewModel.productsRanking.asFlow().first()

        assertThat(productsRanking).isEqualTo(
            listOf(
                Product(id = 1, name = "Odżywka 1"),
                Product(id = 2, name = "Szampon 1"),
                Product(id = 3, name = "Odżywka 2"),
            )
        )
    }
}