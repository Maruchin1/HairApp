package com.example.product_details.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.setVisibleOrGone
import com.example.product_details.R
import com.example.product_details.databinding.FragmentIngredientsBinding
import com.example.product_details.model.PageState
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
        setupEditSaveConfirmButton()
        setupNoIngredients()
        setupChoiceChips()
        observeState()
    }

    private fun setupEditSaveConfirmButton() = binding.header.apply {
        onEditClicked = {
            beginTransitions()
            viewModel.onEditIngredients()
        }
        onAcceptClicked = {
            beginTransitions()
            viewModel.onConfirmIngredients()
        }
    }

    private fun beginTransitions() {
        val activity = requireActivity() as ProductDetailsActivity
        TransitionManager.beginDelayedTransition(activity.binding.root)
    }

    private fun setupNoIngredients() = binding.noIngredients.apply {
        icon.setImageResource(R.drawable.ic_round_category_24)
        message.setText(R.string.no_ingredients_message)
    }

    private fun setupChoiceChips() = binding.apply {
        proteinsChoiceChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onProteinsSelectedChanged(isChecked)
        }
        emollientsChoiceChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEmollientsSelectedChanged(isChecked)
        }
        humectantsChoiceChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHumectantsSelectedChanged(isChecked)
        }
    }

    private fun observeState() = lifecycleScope.launchWhenStarted {
        viewModel.state.collectLatest {
            Log.d("MyDebug", "Ingredients state changed")
            updateHeaderMode(it)
            updateLayoutsVisibility(it)
            updateDisplayedChips(it)
            updateSelectedChoiceChips(it)
        }
    }

    private fun updateHeaderMode(state: PageState) {
        binding.header.mode = state.ingredientsMode
    }

    private fun updateLayoutsVisibility(state: PageState) = binding.apply {
        val mode = state.ingredientsMode
        ingredientsChipGroup.setVisibleOrGone(mode == SectionMode.DISPLAY && state.hasIngredients)
        noIngredients.root.setVisibleOrGone(mode == SectionMode.DISPLAY && !state.hasIngredients)
        ingredientsChoiceChipGroup.setVisibleOrGone(mode == SectionMode.EDIT)
    }

    private fun updateDisplayedChips(state: PageState) = binding.apply {
        val ingredients = state.product?.ingredients
        proteinsChip.setVisibleOrGone(ingredients?.proteins == true)
        emollientsChip.setVisibleOrGone(ingredients?.emollients == true)
        humectantsChip.setVisibleOrGone(ingredients?.humectants == true)
    }

    private fun updateSelectedChoiceChips(state: PageState) = binding.apply {
        val ingredients = state.product?.ingredients
        proteinsChoiceChip.isChecked = ingredients?.proteins == true
        emollientsChoiceChip.isChecked = ingredients?.emollients == true
        humectantsChoiceChip.isChecked = ingredients?.humectants == true
    }
}