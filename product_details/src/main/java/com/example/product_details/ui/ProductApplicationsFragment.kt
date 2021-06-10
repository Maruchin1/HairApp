package com.example.product_details.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.corev2.entities.Product
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.setVisibleOrGone
import com.example.product_details.R
import com.example.product_details.databinding.FragmentProductApplicationsBinding
import com.example.product_details.model.ProductDetailsViewModel
import com.example.product_details.model.PageMode
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
        setupApplicationsChips()
        setupNoApplications()
        observeState()
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

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.applicationsSectionMode.collectLatest {
                updateLayoutsVisibility(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.applications.collectLatest {
                updateDisplayedChips(it)
                updateSelectedChoiceChips(it)
            }
        }
    }

    private fun updateLayoutsVisibility(sectionMode: SectionMode) = binding.apply {
        applicationsChipGroup.isVisible = sectionMode == SectionMode.DISPLAY
        noApplications.root.isVisible = sectionMode == SectionMode.NO_CONTENT
        applicationsChoiceChipGroup.isVisible = sectionMode == SectionMode.EDIT
    }

    private fun updateDisplayedChips(
        applications: Set<Product.Application>
    ) = binding.applicationsChipGroup.apply {
        removeAllViews()
        applications.forEach { application ->
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
        applications: Set<Product.Application>
    ) = binding.applicationsChoiceChipGroup.apply {
        children.forEach { view ->
            val chip = view as Chip
            chip.isChecked = applications
                .map { getString(it.resId) }
                .contains(chip.text)
        }
    }
}