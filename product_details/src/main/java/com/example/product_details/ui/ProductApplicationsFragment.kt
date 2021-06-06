package com.example.product_details.ui

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import com.airbnb.paris.extensions.style
import com.example.corev2.entities.Product
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.setVisibleOrGone
import com.example.product_details.R
import com.example.product_details.databinding.FragmentProductApplicationsBinding
import com.example.product_details.model.PageState
import com.example.product_details.model.ProductDetailsViewModel
import com.example.product_details.model.SectionMode
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class ProductApplicationsFragment : BaseFragment<FragmentProductApplicationsBinding>(
    bindingInflater = FragmentProductApplicationsBinding::inflate
) {

    private val viewModel by sharedViewModel<ProductDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEditConfirmButton()
        setupApplicationsChips()
        setupNoApplications()
        observeState()
    }

    private fun setupEditConfirmButton() = binding.header.apply {
        onEditClicked = {
            beginTransitions()
            viewModel.onEditApplications()
        }
        onAcceptClicked = {
            beginTransitions()
            viewModel.onConfirmApplications()
        }
    }

    private fun beginTransitions() {
        val activity = requireActivity() as ProductDetailsActivity
        TransitionManager.beginDelayedTransition(activity.binding.root)
    }

    private fun setupApplicationsChips() = binding.applicationsChoiceChipGroup.apply {
        removeAllViews()
        Product.Application.values().forEach { application ->
            val chip = Chip(context).apply {
                setText(application.resId)
                setOnCheckedChangeListener { _, isChecked ->
                    viewModel.onApplicationSelectionChanged(application, isChecked)
                }
            }
            addView(chip)
        }
    }

    private fun setupNoApplications() = binding.noApplications.apply {
        icon.setImageResource(R.drawable.ic_round_bathtub_24)
        message.setText(R.string.no_applications_message)
    }

    private fun observeState() = lifecycleScope.launchWhenStarted {
        viewModel.state.collectLatest {
            updateHeaderMode(it)
            updateLayoutsVisibility(it)
            updateDisplayedChips(it)
            updateSelectedChoiceChips(it)
        }
    }

    private fun updateHeaderMode(state: PageState) {
        binding.header.mode = state.productApplicationsMode
    }

    private fun updateLayoutsVisibility(state: PageState) = binding.apply {
        val mode = state.productApplicationsMode
        applicationsChipGroup.setVisibleOrGone(mode == SectionMode.DISPLAY && state.hasApplications)
        noApplications.root.setVisibleOrGone(mode == SectionMode.DISPLAY && !state.hasApplications)
        applicationsChoiceChipGroup.setVisibleOrGone(mode == SectionMode.EDIT)
    }

    private fun updateDisplayedChips(state: PageState) = binding.applicationsChipGroup.apply {
        removeAllViews()
        state.product?.applications?.forEach { application ->
            val chip = Chip(context).apply {
                setText(application.resId)
                isCheckable = false
                setChipBackgroundColorResource(R.color.color_primary)
                setTextColor(ContextCompat.getColor(context, R.color.color_white))
            }
            addView(chip)
        }
    }

    private fun updateSelectedChoiceChips(
        state: PageState
    ) = binding.applicationsChoiceChipGroup.apply {
        children.forEach { view ->
            val chip = view as Chip
            chip.isChecked = state.product
                ?.applications
                ?.map { getString(it.resId) }
                ?.contains(chip.text)
                ?: false
        }
    }
}