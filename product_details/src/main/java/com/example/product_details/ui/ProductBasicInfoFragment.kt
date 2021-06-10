package com.example.product_details.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.setVisibleOrGone
import com.example.product_details.R
import com.example.product_details.databinding.FragmentProductBasicInfoBinding
import com.example.product_details.model.ProductDetailsViewModel
import com.example.product_details.model.PageMode
import com.example.product_details.model.SectionMode
import com.example.shared_ui.extensions.setTextIfChanged
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class ProductBasicInfoFragment : BaseFragment<FragmentProductBasicInfoBinding>(
    bindingInflater = FragmentProductBasicInfoBinding::inflate
) {

    private val viewModel by sharedViewModel<ProductDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNoBasicInfo()
        setupInputs()
        observeState()
    }

    private fun setupNoBasicInfo() = binding.apply {
        noBasicInfo.icon.setImageResource(R.drawable.ic_round_title_24)
        noBasicInfo.message.setText(R.string.no_basic_info_message)
    }

    private fun setupInputs() = binding.apply {
        productNameInput.doAfterTextChanged {
            viewModel.onProductNameInputChanged(it.toString())
        }
        manufacturerInput.doAfterTextChanged {
            viewModel.onManufacturerInputChanged(it.toString())
        }
    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.basicInfoSectionMode.collectLatest {
                updateLayoutsVisibility(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.productName.collectLatest {
                updateProductName(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.manufacturer.collectLatest {
                updateManufacturer(it)
            }
        }
    }

    private fun updateLayoutsVisibility(sectionMode: SectionMode) = binding.apply {
        productName.isVisible = sectionMode == SectionMode.DISPLAY
        manufacturer.isVisible = sectionMode == SectionMode.DISPLAY
        noBasicInfo.root.isVisible = sectionMode == SectionMode.NO_CONTENT
        productNameInputLayout.isVisible = sectionMode == SectionMode.EDIT
        manufacturerInputLayout.isVisible = sectionMode == SectionMode.EDIT
    }

    private fun updateProductName(productName: String) {
        binding.productName.text = productName
        binding.productNameInput.setText(productName)
    }

    private fun updateManufacturer(manufacturer: String) {
        binding.manufacturer.text = manufacturer
        binding.manufacturerInput.setText(manufacturer)
    }
}