package com.example.hairapp.page_care_form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityCareFormBinding
import com.example.hairapp.framework.RecyclerMutableLiveAdapter
import com.example.hairapp.framework.CustomItemTouchHelperCallback
import com.example.hairapp.framework.bind
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_care_form.*

@AndroidEntryPoint
class CareFormActivity : AppCompatActivity() {

    private val viewModel: CareFormViewModel by viewModels()

    fun selectDate() {
        MaterialDatePicker.Builder
            .datePicker()
            .build()
            .show(supportFragmentManager, "DatePicker")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityCareFormBinding>(R.layout.activity_care_form, viewModel)

        input_date.inputType = 0
        input_care_method.inputType = 0

        val items =
            listOf("Product 1", "Product 2", "Product 3", "Product 4", "Product 5", "Product 6")
        val source = MutableLiveData(items)
        val adapter = RecyclerMutableLiveAdapter(
            controller = this,
            lifecycleOwner = this,
            layoutResId = R.layout.item_care_product,
            mutableSource = source,
            compareItemsBy = { it }
        )
        recycler.adapter = adapter
        val touchHelperCallback = CustomItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(touchHelperCallback)
        touchHelper.attachToRecyclerView(recycler)
    }
}