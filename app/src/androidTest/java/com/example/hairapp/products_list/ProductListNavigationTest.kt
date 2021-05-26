package com.example.hairapp.products_list

import com.example.hairapp.R
import org.junit.Test

class ProductListNavigationTest : ProductsListTest() {

    @Test
    fun openNewProductForm() {
        listScreen {
            addProductButton.click()
        }
        productFormScreen {
            toolbar.hasTitle(R.string.new_product)
        }
    }

}