package com.example.product_details.model

import androidx.lifecycle.viewModelScope
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.example.shared_ui.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class ProductDetailsViewModel(
    private val actions: Actions,
    private val productDao: ProductDao,
) : BaseViewModel<PageState>(PageState()) {

    override fun onCleared() {
        super.onCleared()
        GlobalScope.launch { updateProductInDb() }
    }

    fun onProductSelected(productId: Long) = viewModelScope.launch {
        productDao.getById(productId)
            .filterNotNull()
            .first()
            .let { product ->
                reduce { it.copy(product = product) }
            }
    }

    fun onCapturePhoto() = viewModelScope.launch {
        actions.captureProductPhoto()?.let { capturedProductPhoto ->
            reduce { it.copy(product = it.product?.copy(photoData = capturedProductPhoto)) }
            updateProductInDb()
        }
    }

    fun onRemoveProductPhoto() = viewModelScope.launch {
        val confirmed = actions.confirmProductPhotoDeletion()
        if (confirmed) {
            reduce { it.copy(product = it.product?.copy(photoData = null)) }
            updateProductInDb()
        }
    }

    fun onDeleteProduct() = viewModelScope.launch {
        val confirmed = actions.confirmProductDeletion()
        if (confirmed) {
            state.value.product?.let { productDao.delete(it) }
            reduce { it.copy(product = null, productDeleted = true) }
        }
    }

    fun onEditBasicInfo() = viewModelScope.launch {
        reduce { it.copy(basicInfoMode = SectionMode.EDIT) }
    }

    fun onConfirmBasicInfo() = viewModelScope.launch {
        reduce { it.copy(basicInfoMode = SectionMode.DISPLAY) }
        updateProductInDb()
    }

    fun onProductNameInputChanged(newName: String) = viewModelScope.launch {
        reduce { it.copy(product = it.product?.copy(name = newName)) }
    }

    fun onManufacturerInputChanged(newManufacturer: String) = viewModelScope.launch {
        reduce { it.copy(product = it.product?.copy(manufacturer = newManufacturer)) }
    }

    fun onEditIngredients() = viewModelScope.launch {
        reduce { it.copy(ingredientsMode = SectionMode.EDIT) }
    }

    fun onConfirmIngredients() = viewModelScope.launch {
        reduce { it.copy(ingredientsMode = SectionMode.DISPLAY) }
        updateProductInDb()
    }

    fun onProteinsSelectedChanged(selected: Boolean) = viewModelScope.launch {
        reduce {
            it.copy(
                product = it.product?.copy(
                    ingredients = it.product.ingredients.copy(
                        proteins = selected
                    )
                )
            )
        }
    }

    fun onEmollientsSelectedChanged(selected: Boolean) = viewModelScope.launch {
        reduce {
            it.copy(
                product = it.product?.copy(
                    ingredients = it.product.ingredients.copy(
                        emollients = selected
                    )
                )
            )
        }
    }

    fun onHumectantsSelectedChanged(selected: Boolean) = viewModelScope.launch {
        reduce {
            it.copy(
                product = it.product?.copy(
                    ingredients = it.product.ingredients.copy(
                        humectants = selected
                    )
                )
            )
        }
    }

    private suspend fun updateProductInDb() {
        state.value.product?.let {
            productDao.update(it)
        }
    }

    interface Actions {
        suspend fun captureProductPhoto(): String?
        suspend fun confirmProductPhotoDeletion(): Boolean
        suspend fun confirmProductDeletion(): Boolean
    }
}