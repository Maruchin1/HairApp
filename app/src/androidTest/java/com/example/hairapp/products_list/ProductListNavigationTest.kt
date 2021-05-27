package com.example.hairapp.products_list

import com.example.hairapp.R
import com.example.hairapp.screen.ProductFormScreen
import com.example.hairapp.screen.ProductsListScreen
import org.junit.Test

class ProductListNavigationTest : ProductsListTest() {

    @Test
    fun openNewProductForm() {
        ProductsListScreen {
            addProductButton.click()
        }
        ProductFormScreen {
            toolbar.hasTitle(R.string.new_product)
        }
    }

}