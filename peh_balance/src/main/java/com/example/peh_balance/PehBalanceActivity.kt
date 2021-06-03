package com.example.peh_balance

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.corev2.ui.BaseActivity
import com.example.corev2.ui.SystemColors
import com.example.peh_balance.databinding.ActivityPehBalanceBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PehBalanceActivity : BaseActivity<ActivityPehBalanceBinding>(
    bindingInflater = ActivityPehBalanceBinding::inflate
) {

    private val viewModel: PehBalanceViewModel by viewModel()
    private val dialog: Dialog by inject()

    fun selectCaresForBalance() = lifecycleScope.launch {
//        dialog.selectCaresForBalance(
//            context = this@PehBalanceActivity,
//            currentValue = viewModel.getCaresForBalance()
//        )?.let { newCaresForBalance ->
//            viewModel.setCaresForBalance(newCaresForBalance)
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        PehBalanceChartMediator(
//            activity = this,
//            pieChart = binding.pehBalanceChart,
//            source = viewModel.pehBalance
//        )
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.allLight()
    }
}