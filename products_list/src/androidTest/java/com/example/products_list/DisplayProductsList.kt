package com.example.products_list

import com.example.corev2.room_database.HairAppDatabase
import com.example.e2e_test.KProductsListScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DisplayProductsList {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    val screen = KProductsListScreen()

    @Inject
    lateinit var database: HairAppDatabase

    @Before
    fun before() {
        hiltRule.inject()
        database.clearAllTables()
    }

    @Test
    fun displayNoProducts_WhenNoProductsInDb() {
        screen {
            productsRecycler {
                hasSize(0)
            }
            noProducts.isDisplayed()
            noProductsIcon {
                isDisplayed()
                hasDrawable(R.drawable.ic_round_shopping_basket_24)
            }
            noProductMessage {
                isDisplayed()
                hasText(R.string.no_products_message)
            }
        }
    }
}