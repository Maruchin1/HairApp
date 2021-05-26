package com.example.hairapp.products_list

import com.example.hairapp.R
import com.example.hairapp.screen.KProductItem
import org.junit.Test


class DisplayProductsTest : ProductsListTest() {

    @Test
    fun noProductsInDb() {
        listScreen {
            productsRecycler {
                hasSize(0)
            }
            noProducts {
                isDisplayed()
            }
            noProductMessage {
                hasText(R.string.no_products_message)
            }
        }
    }

    @Test
    fun displayProductsFromDb() {
        populateProducts()
        listScreen {
            productsRecycler {
                hasSize(2)

                childAt<KProductItem>(0) {
                    productName.hasText("Super odżywka")
                    manufacturer.hasText("Producent 2")
                }

                childAt<KProductItem>(1) {
                    productName.hasText("Super szampon")
                    manufacturer.hasText("Producent 1")
                }
            }
            noProducts {
                isNotDisplayed()
            }
        }
    }
}