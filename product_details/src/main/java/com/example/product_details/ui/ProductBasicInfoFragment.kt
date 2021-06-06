package com.example.product_details.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.setVisibleOrGone
import com.example.product_details.R
import com.example.product_details.databinding.FragmentProductBasicInfoBinding
import com.example.product_details.model.PageState
import com.example.product_details.model.ProductDetailsViewModel
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
        setupEditConfirmButton()
        setupNoBasicInfo()
        setupInputs()
        observeState()
    }

    private fun setupEditConfirmButton() {
        binding.header.apply {
            onEditClicked = {
                beginTransitions()
                viewModel.onEditBasicInfo()
            }
            onAcceptClicked = {
                beginTransitions()
                viewModel.onConfirmBasicInfo()
            }
        }
    }

    private fun beginTransitions() {
        val activity = requireActivity() as ProductDetailsActivity
        TransitionManager.beginDelayedTransition(activity.binding.root)
    }

    private fun setupNoBasicInfo() = binding.apply {
        noBasicInfo.icon.setImageResource(R.drawable.ic_round_title_24)
        noBasicInfo.message.setText(R.string.no_basic_info_message)
    }

    private fun setupInputs() {
        binding.apply {
            productNameInput.doAfterTextChanged {
                viewModel.onProductNameInputChanged(it.toString())
            }
            manufacturerInput.doAfterTextChanged {
                viewModel.onManufacturerInputChanged(it.toString())
            }
        }
    }

    private fun observeState() = lifecycleScope.launchWhenStarted {
        viewModel.state.collectLatest {
            Log.d("MyDebug", "BasicInfo state changed")
            updateHeaderMode(it)
            updateLayoutsVisibility(it)
            updateDisplayedData(it)
            updateInputs(it)
        }
    }

    private fun updateHeaderMode(state: PageState) {
        binding.header.mode = state.basicInfoMode
    }

    private fun updateLayoutsVisibility(state: PageState) {
        val mode = state.basicInfoMode
        binding.apply {
            productName.setVisibleOrGone(mode == SectionMode.DISPLAY && state.hasBasicInfo)
            manufacturer.setVisibleOrGone(mode == SectionMode.DISPLAY && state.hasBasicInfo)
            noBasicInfo.root.setVisibleOrGone(mode == SectionMode.DISPLAY && !state.hasBasicInfo)
            productNameInputLayout.setVisibleOrGone(mode == SectionMode.EDIT)
            manufacturerInputLayout.setVisibleOrGone(mode == SectionMode.EDIT)
        }
    }

    private fun updateDisplayedData(state: PageState) {
        binding.apply {
            productName.text = state.product?.name
            manufacturer.text = state.product?.manufacturer
        }
    }

    private fun updateInputs(state: PageState) {
        binding.apply {
            productNameInput.setTextIfChanged(state.product?.name)
            manufacturerInput.setTextIfChanged(state.product?.manufacturer)
        }
    }
}