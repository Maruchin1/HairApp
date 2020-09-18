package com.example.hairapp.page_care_form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.core.domain.CareProduct
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityCareFormBinding
import com.example.hairapp.framework.bind
import com.example.hairapp.framework.datePickerDialog
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_care_form.*

@AndroidEntryPoint
class CareFormActivity : AppCompatActivity() {

    private val viewModel: CareFormViewModel by viewModels()

    fun selectDate() = datePickerDialog {
        viewModel.date.value = it.toString()
    }

    fun addProduct() {
        val fragment = fragment_products as CareFormProductsFragment
        fragment.addProduct()
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