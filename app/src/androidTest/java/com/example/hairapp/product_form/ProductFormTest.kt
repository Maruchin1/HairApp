package com.example.hairapp.product_form

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.corev2.entities.Ingredients
import com.example.corev2.entities.Product
import com.example.corev2.room_database.HairAppDatabase
import com.example.hairapp.MainActivity
import com.example.hairapp.screen.HomeScreen
import com.example.hairapp.screen.ProductsListScreen
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.inject

abstract class ProductFormTest : KoinTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    protected val database: HairAppDatabase by inject()

    @Before
    fun before() {
        database.clearAllTables()
        populateProducts()
        HomeScreen {
            productsListButton.click()
        }
        ProductsListScreen {
            addProductButton.click()
        }
    }

    protected fun populateProducts() = runBlocking {
        database.productDao().insert(
            Product(
                name = "Super szampon",
                manufacturer = "Producent testowy",
                ingredients = Ingredients(
                    proteins = true
                ),
                applications = setOf(Product.Application.MEDIUM_SHAMPOO)
            ),
        )
    }
}