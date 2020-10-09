package com.example.hairapp.page_care

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.domain.CareStep
import com.example.core.domain.Product
import com.example.core.domain.ProductsProportion
import com.example.hairapp.framework.BindingRecyclerAdapter
import java.util.*

class CareStepsAdapter(
    controller: Any,
    layoutResId: Int,
) : BindingRecyclerAdapter<CareStep>(controller, layoutResId) {

    private val _productsProportion = MutableLiveData<ProductsProportion>()

    val productsProportion: LiveData<ProductsProportion> = _productsProportion

    fun getCareProduct(position: Int): CareStep? {
        return itemsList.getOrNull(position)
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
        updateItemsOrder()
    }

    fun addStep() {
        val newStepPosition = 0
        itemsList.add(newStepPosition, CareStep(order = newStepPosition))
        notifyItemInserted(newStepPosition)
        updateProductsProportion()
        updateItemsOrder()
    }

    fun removeStep(position: Int) {
        itemsList.removeAt(position)
        notifyItemRemoved(position)
        updateProductsProportion()
        updateItemsOrder()
    }

    fun setStepProduct(position: Int, selectedProduct: Product) {
        val careStep = itemsList[position]
        careStep.product = selectedProduct
        itemsList.removeAt(position)
        itemsList.add(position, careStep)
        notifyItemChanged(position)
        updateProductsProportion()
    }

    override fun updateItems(newList: List<CareStep>?) {
        super.updateItems(newList)
        updateProductsProportion()
        updateItemsOrder()
    }

    private fun updateProductsProportion() {
        _productsProportion.value = ProductsProportion(itemsList)
    }

    private fun updateItemsOrder() {
        itemsList.forEachIndexed { index, careStep ->
            careStep.order = index
        }
    }
}