package com.example.hairapp.page_product_form

import android.net.Uri
import androidx.lifecycle.*
import com.example.core.domain.Product
import com.example.core.use_case.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first

class ProductFormViewModel(
    private val showSelectedProduct: ShowSelectedProduct,
    private val addProduct: AddProduct,
    private val updateProduct: UpdateProduct
) : ViewModel() {

    private val _productNameError = MutableLiveData<String?>(null)

    private var editProductId: Int? = null

    val productApplicationOptions: LiveData<List<Product.Application>> = liveData {
        val optionsNames = Product.Application.values().toList()
        emit(optionsNames)
    }

    val productPhoto = MutableLiveData<Uri?>(null)
    val productName = MutableLiveData("")
    val productManufacturer = MutableLiveData("")
    val humectants = MutableLiveData(false)
    val emollients = MutableLiveData(false)
    val proteins = MutableLiveData(false)
    val productApplications = MutableLiveData(listOf<Product.Application>())

    val productNameError: LiveData<String?> = _productNameError

    fun setEditProductAsync(productId: Int): Deferred<Result<Unit>> = viewModelScope.async {
        editProductId = productId
        val input = ShowSelectedProduct.Input(productId)
        showSelectedProduct(input).runCatching {
            val product = this.first()
            dispatchProductToEdit(product)
        }
    }

    suspend fun saveProduct(): Result<Unit> {
        if (!isFormValid()) return Result.failure(Exception("Niepoprawne dane"))
        return if (editProductId == null) {
            val input = makeAddInput()
            addProduct(input)
        } else {
            val input = makeUpdateInput()
            updateProduct(input)
        }
    }

    private fun isFormValid(): Boolean {
        _productNameError.value = productName.value.let {
            if (it.isNullOrEmpty()) "Podaj nazwę produktu" else null
        }
        if (_productNameError.value != null) return false
        return true
    }

    private fun makeAddInput() = AddProduct.Input(
        productName = productName.value!!,
        productManufacturer = productManufacturer.value!!,
        emollients = emollients.value!!,
        humectants = humectants.value!!,
        proteins = proteins.value!!,
        applications = getSelectedApplications(),
        productPhotoData = productPhoto.value?.toString()
    )

    private fun makeUpdateInput() = UpdateProduct.Input(
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
        emollients.postValue(product.composition.emollients)
        humectants.postValue(product.composition.humectants)
        proteins.postValue(product.composition.proteins)
        productApplications.postValue(product.applications.toList())
    }

    private fun getSelectedApplications(): Set<Product.Application> {
        return productApplications.value?.toSet() ?: return emptySet()
    }
}