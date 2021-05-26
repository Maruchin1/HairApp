package com.example.product_form

import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.MutableLiveData
import com.example.corev2.entities.Product
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


internal class ProductApplicationsMediator(
    private val activity: AppCompatActivity,
    private val chipGroup: ChipGroup,
    private val selectedApplications: MutableLiveData<Set<Product.Application>>
) {

    init {
        setupApplicationsChips()
        setupSelectedApplications()
    }

    private fun setupApplicationsChips() {
        chipGroup.removeAllViews()
        Product.Application.values().forEach { application ->
            val chip = createApplicationChip(application)
            chipGroup.addView(chip)
        }
    }

    private fun createApplicationChip(application: Product.Application) = Chip(activity).apply {
        id = View.generateViewId()
        setText(application.resId)
        isClickable = true
        isCheckable = true
        setOnCheckedChangeListener { buttonView, isChecked ->
            onChipCheckedChange(buttonView, isChecked)
        }
    }

    private fun onChipCheckedChange(buttonView: CompoundButton, isChecked: Boolean) {
        selectedApplications.value?.toMutableSet()?.let {
            val application = getProductApplicationByText(buttonView.text.toString())
            if (isChecked) {
                it.add(application)
            } else {
                it.remove(application)
            }
            selectedApplications.value = it
        }
    }

    private fun setupSelectedApplications() {
        selectedApplications.observe(activity) {
            updateSelectedChips(it)
        }
    }

    private fun updateSelectedChips(selectedApplications: Set<Product.Application>) {
        val chips = chipGroup.children as Sequence<Chip>
        chips.forEach { chip ->
            val chipText = chip.text.toString()
            val chipValue = getProductApplicationByText(chipText)
            chip.isChecked = selectedApplications.contains(chipValue)
        }
    }

    private fun getProductApplicationByText(text: String): Product.Application {
        return when (text) {
            Product.Application.MILD_SHAMPOO.getText() -> Product.Application.MILD_SHAMPOO
            Product.Application.MEDIUM_SHAMPOO.getText() -> Product.Application.MEDIUM_SHAMPOO
            Product.Application.STRONG_SHAMPOO.getText() -> Product.Application.STRONG_SHAMPOO
            Product.Application.CONDITIONER.getText() -> Product.Application.CONDITIONER
            Product.Application.CREAM.getText() -> Product.Application.CREAM
            Product.Application.MASK.getText() -> Product.Application.MASK
            Product.Application.LEAVE_IN_CONDITIONER.getText() -> Product.Application.LEAVE_IN_CONDITIONER
            Product.Application.OIL.getText() -> Product.Application.OIL
            Product.Application.FOAM.getText() -> Product.Application.FOAM
            Product.Application.SERUM.getText() -> Product.Application.SERUM
            Product.Application.GEL.getText() -> Product.Application.GEL
            Product.Application.OTHER.getText() -> Product.Application.OTHER
            else -> throw IllegalArgumentException("No matching Product.Application for text: $text")
        }
    }

    private fun Product.Application.getText(): String {
        return activity.getString(resId)
    }
}