package com.example.product_form

import android.app.Activity
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.example.corev2.ui.ImagePickerService
import com.example.testing.rules.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class ProductFormViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val productForm: ProductForm = mockk()
    private val productDao: ProductDao = mockk()
    private val imagePickerService: ImagePickerService = mockk()
    private val viewModel by lazy {
        ProductFormViewModel(productForm, productDao, imagePickerService)
    }

    @Test
    fun form_ReturnsFormInstance() {
        assertThat(viewModel.form).isSameInstanceAs(productForm)
    }

    @Test
    fun takePhoto_SetPhotoInForm_WhenServiceReturnedPhoto() {
        val activity: Activity = mockk()
        val photoUri: Uri = mockk()
        coEvery { imagePickerService.takeProductPhoto(any()) } returns photoUri
        justRun { productForm.productPhoto.postValue(any()) }

        viewModel.takePhoto(activity)

        verify {
            productForm.productPhoto.postValue(photoUri)
        }
    }

    @Test
    fun takePhoto_DoNothing_WhenPickerCanceled() {
        val activity: Activity = mockk()
        coEvery { imagePickerService.takeProductPhoto(any()) } returns null
        justRun { productForm.productPhoto.postValue(any()) }

        viewModel.takePhoto(activity)

        verify(exactly = 0) {
            productForm.productPhoto.postValue(any())
        }
    }

    @Test
    fun onSaveClick_WhenFormIsValid_InsertNewProductToDb() = runBlocking {
        val newProduct = Product(name = "Test product", manufacturer = "Test manufacturer")
        every { productForm.createProduct() } returns newProduct
        every { productForm.isValid } returns true
        coJustRun { productDao.insert(*anyVararg()) }

        viewModel.onSaveClick()

        coVerify {
            productDao.insert(newProduct)
        }
    }

    @Test
    fun onSaveClick_WhenFormIsNotValid_DoNotInsertNewProductToDb() = runBlocking {
        val newProduct = Product(name = "Test product", manufacturer = "Test manufacturer")
        every { productForm.createProduct() } returns newProduct
        every { productForm.isValid } returns false
        coJustRun { productDao.insert(*anyVararg()) }

        viewModel.onSaveClick()

        coVerify(exactly = 0) {
            productDao.insert(newProduct)
        }
    }
}