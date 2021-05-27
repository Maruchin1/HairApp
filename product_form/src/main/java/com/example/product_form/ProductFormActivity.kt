package com.example.product_form

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.corev2.ui.*
import com.example.product_form.databinding.ActivityProductForm2Binding
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ProductFormActivity : BaseActivity<ActivityProductForm2Binding>() {

    override val inflateBinding: InflateActivityBinding<ActivityProductForm2Binding>
        get() = ActivityProductForm2Binding::inflate

    private val viewModel: ProductFormViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
        setupProductPhoto()
        setupProductFields()
        setupCompositionOfIngredients()
        setupProductApplications()
        setupSaveButton()
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.apply {
            allLight()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            viewModel.form.productPhoto.value = data?.data
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupProductPhoto() {
        binding.productPhoto.let {
            it.card.setOnClickListener { takePhoto() }
            it.image.setPicassoUriSource(viewModel.form.productPhoto, this)
        }
    }

    private fun takePhoto() {
        ImagePicker.with(this)
            .crop(x = 1f, y = 1f)
            .compress(maxSize = 1024)
            .maxResultSize(width = 1080, height = 1080)
            .start()
    }

    private fun setupProductFields() {
        binding.productNameInput.setPicassoUriSource(viewModel.form.productName, this)
        binding.manufacturerInput.setPicassoUriSource(viewModel.form.manufacturer, this)
    }

    private fun setupCompositionOfIngredients() {
        binding.compositionOfIngredients.let {
            it.proteinsChip.setIsCheckedSource(viewModel.form.proteins, this)
            it.emollientsChip.setIsCheckedSource(viewModel.form.emollients, this)
            it.humectantsChip.setIsCheckedSource(viewModel.form.humectants, this)
        }
    }

    private fun setupProductApplications() {
        ProductApplicationsMediator(
            activity = this,
            chipGroup = binding.productApplications.chipGroup,
            selectedApplications = viewModel.form.productApplications
        )
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.onSaveClick()
                finish()
            }
        }
    }

    companion object {
        const val EDIT_PRODUCT_ID = "editProductId"
    }
}