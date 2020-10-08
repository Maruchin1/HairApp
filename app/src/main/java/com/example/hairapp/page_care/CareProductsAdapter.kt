package com.example.hairapp.page_care

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.domain.CareProduct
import com.example.core.domain.Product
import com.example.core.domain.ProductsProportion
import com.example.hairapp.framework.BindingRecyclerAdapter
import java.util.*

class CareProductsAdapter(
    controller: Any,
    layoutResId: Int,
) : BindingRecyclerAdapter<CareProduct>(controller, layoutResId) {

    private val _productsProportion = MutableLiveData<ProductsProportion>()

    val productsProportion: LiveData<ProductsProportion> = _productsProportion

    fun getCareProduct(position: Int): CareProduct? {
        return itemsList.getOrNull(position)
    }

    fun getPosition(careProduct: CareProduct): Int {
        return itemsList.indexOf(careProduct)
    }

    fun getAllCareProducts(): List<CareProduct> {
        return itemsList
    }

    fun moveCareProduct(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(itemsList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(itemsList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun addCareProduct() {
        itemsList.add(0, CareProduct())
        notifyItemInserted(0)
        updateProductsProportion()
    }

    fun removeCareProduct(position: Int) {
        itemsList.removeAt(position)
        notifyItemRemoved(position)
        updateProductsProportion()
    }

    fun setProduct(careProduct: CareProduct, selectedProduct: Product) {
        val position = itemsList.indexOf(careProduct)
        careProduct.product = selectedProduct
        itemsList.removeAt(position)
        itemsList.add(position, careProduct)
        notifyItemChanged(position)
        updateProductsProportion()
    }

    override fun updateItems(newList: List<CareProduct>?) {
        super.updateItems(newList)
        updateProductsProportion()
    }

    private fun updateProductsProportion() {
        _productsProportion.value = ProductsProportion(itemsList)
    }
}