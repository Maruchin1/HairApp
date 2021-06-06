package com.example.product_details.model

import androidx.lifecycle.viewModelScope
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.example.shared_ui.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

internal class ProductDetailsViewModel(
    private val productDao: ProductDao,
    private val captureProductPhotoUseCase: CaptureProductPhotoUseCase,
    private val changeProductBasicInfoUseCase: ChangeProductBasicInfoUseCase
) : BaseViewModel<PageState>(PageState()) {

    fun onProductSelected(productId: Long) = viewModelScope.launch {
        productDao.getById(productId)
            .filterNotNull()
            .collectLatest { updateState(it) }
    }

    fun onCapturePhotoClicked() = viewModelScope.launch {
        captureProductPhotoUseCase(state.value.product?.id ?: -1)
    }

    fun onEditBasicInfoClicked() = viewModelScope.launch {
        reduce { it.copy(basicInfoMode = SectionMode.EDIT) }
    }

    fun onProductNameInputChanged(newName: String) = viewModelScope.launch {
        reduce {
            it.copy(
                basicInfoForm = it.basicInfoForm.copy(
                    productName = newName
                )
            )
        }
    }

    fun onManufacturerInputChanged(newManufacturer: String) = viewModelScope.launch {
        reduce {
            it.copy(
                basicInfoForm = it.basicInfoForm.copy(
                    manufacturer = newManufacturer
                )
            )
        }
    }

    fun onConfirmBasicInfoClicked() = viewModelScope.launch {
        reduce { it.copy(basicInfoMode = SectionMode.DISPLAY) }
        val currentState = state.value
        val product = currentState.product
        val (newName, newManufacturer) = currentState.basicInfoForm
        if (product != null) {
            changeProductBasicInfoUseCase(product.id, newName, newManufacturer)
        }
    }

    private suspend fun updateState(product: Product) = reduce {
        it.copy(
            product = product,
            basicInfoForm = it.basicInfoForm.copy(
                productName = product.name,
                manufacturer = product.manufacturer
            )
        )
    }
}