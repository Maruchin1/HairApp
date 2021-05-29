package com.example.care_details.components

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import arrow.core.computations.either
import com.example.care_details.R
import com.example.care_details.databinding.ActivityCareDetailsBinding
import com.example.corev2.entities.PehBalance
import com.example.corev2.navigation.CareDetailsDestination
import com.example.corev2.navigation.Destination
import com.example.corev2.service.formatDayOfWeekAndMonth
import com.example.corev2.ui.BaseActivity
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
    private val useCaseActions: UseCaseActions by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        useCaseActions.bindToActivity(this)
        setupViewModel()
        setupPehBalanceBar()
        setupToolbar()
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.apply {
            allLight()
        }
    }

    private fun setupViewModel() {
        params?.careId?.let {
            viewModel.onCareSelected(it)
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
                    R.id.select_date -> onChangeDateClicked()
                    R.id.delete_care -> onDeleteCareClicked()
                }
                true
            }
        }
    }

    private fun setupPehBalanceBar() {
        viewModel.steps.observe(this) { steps ->
            val products = steps.mapNotNull { it.product }
            binding.pehBalanceBar.pehBalance = PehBalance.fromProducts(products)
        }
    }

    private fun onChangeDateClicked() = lifecycleScope.launch {
        viewModel.onChangeDateClicked()
    }

    private fun onDeleteCareClicked() = lifecycleScope.launch {
        viewModel.onDeleteCareClicked()
            .map { finish() }
    }
}