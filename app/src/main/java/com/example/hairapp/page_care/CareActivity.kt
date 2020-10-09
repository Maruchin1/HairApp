package com.example.hairapp.page_care

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityCareBinding
import com.example.hairapp.framework.*
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_care.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareActivity : AppCompatActivity() {

    private val viewModel: CareViewModel by viewModel()

    fun selectDate() = datePickerDialog {
        viewModel.setDate(it)
    }

    fun addProduct() {
        val productsFragment = fragment_products as CareProductsFragment
        productsFragment.addProduct()
    }

    fun addPhoto() {
        ImagePicker.with(this)
            .crop(x = 4f, y = 3f)
            .compress(maxSize = 1024)
            .maxResultSize(width = 1080, height = 810)
            .start()
    }

    fun deleteCare() {
        lifecycleScope.launch {
            viewModel.deleteCare().onFailure {
                showErrorSnackbar(it.message)
            }.onSuccess {
                finish()
            }
        }
    }

    fun saveCare() {
        lifecycleScope.launch {
            val fragment = fragment_products as CareProductsFragment
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
        bind<ActivityCareBinding>(R.layout.activity_care, viewModel)
        setStatusBarColor(R.color.color_primary)
        setNavigationColor(R.color.color_white)

        input_date.inputType = 0
        input_care_type.inputType = 0

        setCareTypeListener()
        checkIfEdit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.data?.let { viewModel.addPhoto(it.toString()) }
        }
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
            return Intent(context, CareActivity::class.java)
                .putExtra(IN_EDIT_CARE_ID, editCareId)
        }
    }
}