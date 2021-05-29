package com.example.care_details.components

import android.content.Context
import com.example.care_details.databinding.ItemCareStepBinding
import com.example.corev2.entities.CareStep
import com.example.corev2.entities.Product
import com.example.corev2.relations.CareStepWithProduct
import com.example.corev2.ui.BaseRecyclerAdapter
import com.example.corev2.ui.setPicassoUri
import com.example.corev2.ui.setVisibleOrGone

internal class CareStepsAdapter(
    private val context: Context
) : BaseRecyclerAdapter<CareStepWithProduct, ItemCareStepBinding>(
    bindingInflater = ItemCareStepBinding::inflate
) {

    override fun onSetItemData(binding: ItemCareStepBinding, item: CareStepWithProduct) {
        val productSelected = item.product != null
        if (productSelected) {
            setupSelectedProduct(binding, item.careStep, item.product!!)
        } else {
            setupProductToSelect(binding, item.careStep)
        }
        binding.apply {
            arrayOf(
                productPhoto,
                productType,
                productName,
                manufacturer
            ).forEach { it.setVisibleOrGone(productSelected) }
            arrayOf(
                productToSelectType,
                productToSelectMessage
            ).forEach { it.setVisibleOrGone(!productSelected) }
        }
    }

    private fun setupProductToSelect(binding: ItemCareStepBinding, careStep: CareStep) {
        binding.apply {
            productToSelectType.text = context.getString(careStep.productType.resId)
        }
    }

    private fun setupSelectedProduct(
        binding: ItemCareStepBinding,
        careStep: CareStep,
        product: Product
    ) {
        binding.apply {
            productPhoto.setPicassoUri(product.photoData)
            productType.text = context.getString(careStep.productType.resId)
            productName.text = product.name
            manufacturer.text = product.manufacturer
        }
    }
}