package com.example.hairapp.page_product_form

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.domain.Product
import com.example.core.domain.Application
import com.example.core.invoke
import com.example.core.use_case.*
import com.example.hairapp.R
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first

class ProductFormViewModel @ViewModelInject constructor(
    private val showSelectedProduct: ShowSelectedProduct,
    private val showProductApplicationOptions: ShowProductApplicationOptions,
    private val addProduct: AddProduct,
    private val updateProduct: UpdateProduct
) : ViewModel() {

    private var editProductId: Int? = null

    val photoPlaceholderId: LiveData<Int> = liveData {
        val id = R.drawable.ic_round_add_a_photo_24
        emit(id)
    }
    val productApplicationOptions: LiveData<List<String>> = liveData {
        val options = showProductApplicationOptions().first()
        val optionsNames = options.map { it.name }
        emit(optionsNames)
    }

    val productPhoto = MutableLiveData<Uri?>(null)
    val productName = MutableLiveData("")
    val productManufacturer = MutableLiveData("")
    val humectants = MutableLiveData(false)
    val emollients = MutableLiveData(false)
    val proteins = MutableLiveData(false)
    val productApplications = MutableLiveData(setOf<String>())

    fun setEditProductAsync(productId: Int): Deferred<Result<Unit>> = viewModelScope.async {
        editProductId = productId
        val input = ShowSelectedProduct.Input(productId)
        showSelectedProduct(input).runCatching {
            val product = this.first()
            dispatchProductToEdit(product)
        }
    }

    suspend fun saveProduct(): Result<Unit> {
        return if (editProductId == null) {
            val input = makeAddInput()
            addProduct(input)
        } else {
            val input = makeUpdateInput()
            updateProduct(input)
        }
    }

    private suspend fun makeAddInput() = AddProduct.Input(
        productName = productName.value!!,
        productManufacturer = productManufacturer.value!!,
        emollients = emollients.value!!,
        humectants = humectants.value!!,
        proteins = proteins.value!!,
        applications = getSelectedApplications(),
        productPhotoData = productPhoto.value?.toString()
    )

    private suspend fun makeUpdateInput() = UpdateProduct.Input(
        productId = editProductId!!,
        productName = productName.value!!,
        productManufacturer = productManufacturer.value!!,
        emollients = emollients.value!!,
        humectants = humectants.value!!,
        proteins = proteins.value!!,
        applications = getSelectedApplications(),
        productPhotoData = productPhoto.value?.toString()
    )

    private fun dispatchProductToEdit(product: Product) {
        productPhoto.postValue(product.photoData?.let { Uri.parse(it) })
        productName.postValue(product.name)
        productManufacturer.postValue(product.manufacturer)
        emollients.postValue(product.type.emollients)
        humectants.postValue(product.type.humectants)
        proteins.postValue(product.type.proteins)
        productApplications.postValue(product.applications.map { it.name }.toSet())
    }

    private suspend fun getSelectedApplications(): Set<Application> {
        val selectedNames = productApplications.value ?: return emptySet()
        val possibleApplications = showProductApplicationOptions().first()
        val selectedApplications = possibleApplications.filter { selectedNames.contains(it.name) }
        return selectedApplications.toSet()
    }
}