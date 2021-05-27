package com.example.product_form

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test

class ProductFormTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val form by lazy { ProductForm() }

    @Test
    fun isValid_ReturnFalse_WhenProductNameIsEmpty() {
        form.productName.value = ""

        assertThat(form.isValid).isFalse()
    }

    @Test
    fun isValid_ReturnFalse_WhenProductNameIsBlank() {
        form.productName.value = "   "

        assertThat(form.isValid).isFalse()
    }

    @Test
    fun isValid_OnlyNameRequired() {
        form.productName.value = "Test"

        assertThat(form.isValid).isTrue()
    }
}