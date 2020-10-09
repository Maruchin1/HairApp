package com.example.hairapp.page_care

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.domain.CareStep
import com.example.core.domain.Product
import com.example.core.domain.ProductsProportion
import com.example.hairapp.framework.BindingRecyclerAdapter
import java.util.*

class CareProductsAdapter(
    controller: Any,
    layoutResId: Int,
) : BindingRecyclerAdapter<CareStep>(controller, layoutResId) {

    private val _productsProportion = MutableLiveData<ProductsProportion>()

    val productsProportion: LiveData<ProductsProportion> = _productsProportion

    fun getCareProduct(position: Int): CareStep? {
        return itemsList.getOrNull(position)
    }

    fun getPosition(careStep: CareStep): Int {
        return itemsList.indexOf(careStep)
    }

    fun getAllCareProducts(): List<CareStep> {
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
        itemsList.add(0, CareStep())
        notifyItemInserted(0)
        updateProductsProportion()
    }

    fun removeCareProduct(position: Int) {
        itemsList.removeAt(position)
        notifyItemRemoved(position)
        updateProductsProportion()
    }

    fun setProduct(careStep: CareStep, selectedProduct: Product) {
        val position = itemsList.indexOf(careStep)
        careStep.product = selectedProduct
        itemsList.removeAt(position)
        itemsList.add(position, careStep)
        notifyItemChanged(position)
        updateProductsProportion()
    }

    override fun updateItems(newList: List<CareStep>?) {
        super.updateItems(newList)
        updateProductsProportion()
    }

    private fun updateProductsProportion() {
        _productsProportion.value = ProductsProportion(itemsList)
    }
}