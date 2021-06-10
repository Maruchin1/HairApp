package com.example.product_details.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.corev2.entities.Ingredients
import com.example.corev2.ui.BaseFragment
import com.example.product_details.R
import com.example.product_details.databinding.FragmentIngredientsBinding
import com.example.product_details.model.ProductDetailsViewModel
import com.example.product_details.model.SectionMode
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class IngredientsFragment : BaseFragment<FragmentIngredientsBinding>(
    bindingInflater = FragmentIngredientsBinding::inflate
) {

    private val viewModel by sharedViewModel<ProductDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChoiceChips()
        setupNoIngredients()
        observeState()
    }

    private fun setupChoiceChips() = binding.apply {
        proteinsChoiceChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onProteinsSelectionChanged(isChecked)
        }
        emollientsChoiceChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEmollientsSelectionChanged(isChecked)
        }
        humectantsChoiceChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHumectantsSelectionChanged(isChecked)
        }
    }

    private fun setupNoIngredients() = binding.noIngredients.apply {
        icon.setImageResource(R.drawable.ic_round_category_24)
        message.setText(R.string.no_ingredients_message)
    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.ingredientsSectionMode.collectLatest {
                updateVisibility(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.ingredients.collectLatest {
                updateDisplayedChips(it)
                updateSelectedChoiceChips(it)
            }
        }
    }

    private fun updateVisibility(sectionMode: SectionMode) = binding.apply {
        ingredientsChipGroup.isVisible = sectionMode == SectionMode.DISPLAY
        noIngredients.root.isVisible = sectionMode == SectionMode.NO_CONTENT
        ingredientsChoiceChipGroup.isVisible = sectionMode == SectionMode.EDIT
    }

    private fun updateDisplayedChips(ingredients: Ingredients) = binding.apply {
        proteinsChip.isVisible = ingredients.proteins
        emollientsChip.isVisible = ingredients.emollients
        humectantsChip.isVisible = ingredients.humectants
    }

    private fun updateSelectedChoiceChips(ingredients: Ingredients) = binding.apply {
        proteinsChoiceChip.isChecked = ingredients.proteins
        emollientsChoiceChip.isChecked = ingredients.emollients
        humectantsChoiceChip.isChecked = ingredients.humectants
    }
}