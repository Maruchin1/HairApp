package com.example.hairapp.page_peh_balance

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityPehBalanceBinding
import com.example.hairapp.framework.Dialog
import com.example.hairapp.framework.SystemColors
import com.example.hairapp.framework.bindActivity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PehBalanceActivity : AppCompatActivity() {

    private val viewModel: PehBalanceViewModel by viewModel()
    private val dialog: Dialog by inject()
    private lateinit var binding: ActivityPehBalanceBinding

    fun selectCaresForBalance() = lifecycleScope.launch {
        dialog.selectCaresForBalance(
            context = this@PehBalanceActivity,
            currentValue = viewModel.getCaresForBalance()
        )?.let { newCaresForBalance ->
            viewModel.setCaresForBalance(newCaresForBalance)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindActivity(R.layout.activity_peh_balance, viewModel)
        SystemColors(this).allDark().apply()

        PehBalanceChartMediator(
            activity = this,
            pieChart = binding.pehBalanceChart,
            source = viewModel.pehBalance
        )
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, PehBalanceActivity::class.java))
        }
    }
}