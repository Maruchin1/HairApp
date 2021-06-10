package com.example.product_details.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Ingredients
import com.example.corev2.entities.Product
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class ProductDetailsViewModel(
    private val productDao: ProductDao,
    private val captureProductPhotoUseCase: CaptureProductPhotoUseCase,
    private val removeProductPhotoUseCase: RemoveProductPhotoUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) : ViewModel() {

    private val _productId = MutableStateFlow<Long?>(null)
    private val _pageMode = MutableStateFlow(PageMode.DISPLAY)
    private val _productPhotoData = MutableStateFlow<String?>(null)
    private val _productName = MutableStateFlow("")
    private val _manufacturer = MutableStateFlow("")
    private val _ingredients = MutableStateFlow(Ingredients())
    private val _applications = MutableStateFlow(emptySet<Product.Application>())
    private val _updateInput = MutableStateFlow(UpdateProductUseCase.Input())

    val pageMode: Flow<PageMode> = _pageMode
    val productPhotoData: Flow<String?> = _productPhotoData
    val productName: Flow<String> = _productName
    val manufacturer: Flow<String> = _manufacturer
    val ingredients: Flow<Ingredients> = _ingredients
    val applications: Flow<Set<Product.Application>> = _applications

    val basicInfoSectionMode: Flow<SectionMode> = combine(
        productName,
        manufacturer,
        pageMode
    ) { a, b, c -> getBasicInfoSectionMode(a, b, c) }

    val ingredientsSectionMode: Flow<SectionMode> = combine(
        ingredients,
        pageMode
    ) { a, b -> getIngredientsSectionMode(a, b) }

    val applicationsSectionMode: Flow<SectionMode> = combine(
        applications,
        pageMode
    ) { a, b -> getApplicationsSectionMode(a, b) }

    fun onProductSelected(productId: Long) = viewModelScope.launch {
        _productId.emit(productId)
        productDao.getById(productId)
            .filterNotNull()
            .collectLatest {
                _productPhotoData.emit(it.photoData)
                _productName.emit(it.name)
                _manufacturer.emit(it.manufacturer)
                _ingredients.emit(it.ingredients)
                _applications.emit(it.applications)
                val newInput = _updateInput.value.copy(
                    newProductName = it.name,
                    newManufacturer = it.manufacturer,
                    newIngredients = it.ingredients,
                    newApplications = it.applications
                )
                _updateInput.emit(newInput)
            }
    }

    fun onEditProduct() = viewModelScope.launch {
        _pageMode.emit(PageMode.EDIT)
    }

    fun onDeleteProduct() = viewModelScope.launch {
        _productId.value?.let {
            deleteProductUseCase(it)
        }
    }

    fun onFinishEdition() = GlobalScope.launch {
        _pageMode.emit(PageMode.DISPLAY)
        _productId.value?.let {
            updateProductUseCase(it, _updateInput.value)
        }
    }

    fun onCapturePhoto() = viewModelScope.launch {
        _productId.value?.let {
            captureProductPhotoUseCase(it)
        }
    }

    fun onRemoveProductPhoto() = viewModelScope.launch {
        _productId.value?.let {
            removeProductPhotoUseCase(it)
        }
    }

    fun onProductNameInputChanged(newName: String) = viewModelScope.launch {
        val newInput = _updateInput.value.copy(
            newProductName = newName
        )
        _updateInput.emit(newInput)
    }

    fun onManufacturerInputChanged(newManufacturer: String) = viewModelScope.launch {
        val newInput = _updateInput.value.copy(
            newManufacturer = newManufacturer
        )
        _updateInput.emit(newInput)
    }

    fun onProteinsSelectionChanged(selected: Boolean) = viewModelScope.launch {
        val newInput = _updateInput.value.let {
            it.copy(
                newIngredients = it.newIngredients.copy(
                    proteins = selected
                )
            )
        }
        _updateInput.emit(newInput)
    }

    fun onEmollientsSelectionChanged(selected: Boolean) = viewModelScope.launch {
        val newInput = _updateInput.value.let {
            it.copy(
                newIngredients = it.newIngredients.copy(
                    emollients = selected
                )
            )
        }
        _updateInput.emit(newInput)
    }

    fun onHumectantsSelectionChanged(selected: Boolean) = viewModelScope.launch {
        val newInput = _updateInput.value.let {
            it.copy(
                newIngredients = it.newIngredients.copy(
                    humectants = selected
                )
            )
        }
        _updateInput.emit(newInput)
    }

    fun onApplicationSelectionChanged(
        application: Product.Application,
        selected: Boolean
    ) = viewModelScope.launch {
        val newInput = _updateInput.value.let {
            it.copy(
                newApplications = if (selected) {
                    it.newApplications + application
                } else {
                    it.newApplications - application
                }
            )
        }
        _updateInput.emit(newInput)
    }

    private fun getBasicInfoSectionMode(
        productName: String,
        manufacturer: String,
        pageMode: PageMode
    ) = when {
        pageMode == PageMode.EDIT ->
            SectionMode.EDIT
        productName.isEmpty() && manufacturer.isEmpty() && pageMode == PageMode.DISPLAY ->
            SectionMode.NO_CONTENT
        else ->
            SectionMode.DISPLAY
    }

    private fun getIngredientsSectionMode(ingredients: Ingredients, pageMode: PageMode) = when {
        pageMode == PageMode.EDIT ->
            SectionMode.EDIT
        !ingredients.hasIngredients && pageMode == PageMode.DISPLAY ->
            SectionMode.NO_CONTENT
        else ->
            SectionMode.DISPLAY
    }

    private fun getApplicationsSectionMode(
        applications: Set<Product.Application>,
        pageMode: PageMode
    ) = when {
        pageMode == PageMode.EDIT ->
            SectionMode.EDIT
        applications.isEmpty() && pageMode == PageMode.DISPLAY ->
            SectionMode.NO_CONTENT
        else ->
            SectionMode.DISPLAY
    }
}