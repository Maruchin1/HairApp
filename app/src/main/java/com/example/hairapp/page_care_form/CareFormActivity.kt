package com.example.hairapp.page_care_form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityCareFormBinding
import com.example.hairapp.framework.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_care_form.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CareFormActivity : AppCompatActivity() {

    private val viewModel: CareFormViewModel by viewModels()

    fun selectDate() = datePickerDialog {
        viewModel.setDate(it)
    }

    fun addProduct() {
        val productsFragment = fragment_products as CareFormProductsFragment
        productsFragment.addProduct()
    }

    fun saveCare() {
        lifecycleScope.launch {
            val fragment = fragment_products as CareFormProductsFragment
            val steps = fragment.getCareProducts()
            viewModel.saveCare(steps).onFailure {
                showErrorSnackbar(it.message)
            }.onSuccess {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityCareFormBinding>(R.layout.activity_care_form, viewModel)
        setStatusBarColor(R.color.color_primary)
        setNavigationColor(R.color.color_white)

        input_date.inputType = 0
        input_care_type.inputType = 0

        setCareTypeListener()
        checkIfEdit()
    }

    private fun setCareTypeListener() {
        input_care_type.doOnTextChanged { text, _, _, _ ->
            Converter.inverseCareType(text?.toString())?.let {
                viewModel.setCareType(it)
            }
        }
    }

    private fun checkIfEdit() {
        val editCareId = intent.getIntExtra(IN_EDIT_CARE_ID, -1)
        if (editCareId != -1)
            setEditCare(editCareId)
    }

    private fun setEditCare(careId: Int) = lifecycleScope.launch {
        toolbar.title = "Pielęgnacja"
        viewModel.setEditCareAsync(careId)
            .await()
            .onFailure { showErrorSnackbar(it.message) }
    }

    companion object {
        private const val IN_EDIT_CARE_ID = "in-edit-care-id"

        fun makeIntent(context: Context, editCareId: Int?): Intent {
            return Intent(context, CareFormActivity::class.java)
                .putExtra(IN_EDIT_CARE_ID, editCareId)
        }
    }
}