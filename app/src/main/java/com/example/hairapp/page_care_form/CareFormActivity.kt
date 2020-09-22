package com.example.hairapp.page_care_form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityCareFormBinding
import com.example.hairapp.framework.bind
import com.example.hairapp.framework.datePickerDialog
import com.example.hairapp.framework.showErrorSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_care_form.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CareFormActivity : AppCompatActivity() {

    private val viewModel: CareFormViewModel by viewModels()

    fun selectDate() = datePickerDialog {
        viewModel.date.value = it.toString()
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

        input_date.inputType = 0
        input_care_method.inputType = 0

    }

    companion object {
        fun makeIntent(context: Context): Intent {
            return Intent(context, CareFormActivity::class.java)
        }
    }
}