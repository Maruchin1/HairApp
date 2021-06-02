package com.example.care_details.components

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.care_details.databinding.FragmentCareStepsBinding
import com.example.care_details.databinding.ItemCareStepBinding
import com.example.corev2.entities.CareStep
import com.example.corev2.entities.Product
import com.example.corev2.relations.CareStepWithProduct
import com.example.corev2.ui.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class CareStepsFragment : BaseFragment<FragmentCareStepsBinding>(
    bindingInflater = FragmentCareStepsBinding::inflate
) {

    private val viewModel: CareDetailsViewModel by sharedViewModel()
    private val adapter by lazy { CareStepsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStepsRecycler()
    }

    private fun setupStepsRecycler() {
        binding.stepsRecycler.adapter = adapter
        adapter.addSource(viewModel.steps, viewLifecycleOwner)
        adapter.setItemComparator { oldItem, newItem -> oldItem.careStep.id == newItem.careStep.id }
    }

    private inner class CareStepsAdapter :
        BaseRecyclerAdapter<CareStepWithProduct, ItemCareStepBinding>(
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
                card.setOnClickListener { onCareStepClicked(item.careStep) }
            }
        }

        private fun setupSelectedProduct(
            binding: ItemCareStepBinding,
            careStep: CareStep,
            product: Product
        ) {
            binding.apply {
                productPhoto.setPicassoUri(product.photoData)
                productType.text = careStep.productType
                    ?.let { requireContext().getString(it.resId) }
                productName.text = product.name
                manufacturer.text = product.manufacturer
            }
        }

        private fun setupProductToSelect(binding: ItemCareStepBinding, careStep: CareStep) {
            binding.apply {
                productToSelectType.text = careStep.productType
                    ?.let { requireContext().getString(it.resId) }
            }
        }

        private fun onCareStepClicked(step: CareStep) = lifecycleScope.launch {
            viewModel.onCareStepClicked(step)
        }
    }
}