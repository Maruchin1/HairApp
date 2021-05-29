package com.example.care_details.components

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.care_details.R
import com.example.care_details.databinding.ActivityCareDetailsBinding
import com.example.corev2.navigation.CareDetailsDestination
import com.example.corev2.navigation.Destination
import com.example.corev2.service.formatDayOfWeekAndMonth
import com.example.corev2.ui.BaseActivity
import com.example.corev2.ui.DialogService
import com.example.corev2.ui.SystemColors
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareDetailsActivity : BaseActivity<ActivityCareDetailsBinding>(
    bindingInflater = ActivityCareDetailsBinding::inflate
) {

    private val params: CareDetailsDestination.Params?
        get() = intent.getParcelableExtra(Destination.EXTRA_PARAMS)

    private val viewModel: CareDetailsViewModel by viewModel()
    private val dialogService: DialogService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.apply {
            allLight()
        }
    }

    private fun setupToolbar() {
        viewModel.careDate.observe(this) {
            binding.toolbar.title = it.formatDayOfWeekAndMonth()
        }
        binding.toolbar.apply {
            setNavigationOnClickListener { finish() }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.select_date -> selectCareDate()
                }
                true
            }
        }
    }

    private fun selectCareDate() = lifecycleScope.launch {
        dialogService.selectDate(
            manager = supportFragmentManager,
            selectedDate = viewModel.careDate.value
        )?.let { selectedDate ->
            viewModel.onCareDateSelected(selectedDate)
        }
    }
}