package com.example.hairapp.page_peh_balance

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.core.domain.PehBalance
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityPehBalanceBinding
import com.example.hairapp.framework.Dialog
import com.example.hairapp.framework.bind
import com.example.hairapp.framework.setSystemColors
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class PehBalanceActivity : AppCompatActivity() {

    private val viewModel: PehBalanceViewModel by viewModel()
    private lateinit var binding: ActivityPehBalanceBinding

    fun selectNumOfCares() = lifecycleScope.launch {
        Dialog.pickNumber(
            context = this@PehBalanceActivity,
            title = "Liczba pielęgnacji",
            minValue = 1,
            maxValue = viewModel.getNumOfAllCares(),
            currentValue = viewModel.getSelectedNumOfCares()
        )?.let { selectedNumber ->
            viewModel.selectNumOfCares(selectedNumber)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bind(R.layout.activity_peh_balance, viewModel)
        setSystemColors(R.color.color_primary)

        PehBalanceChartMediator(
            activity = this,
            pieChart = binding.pehBalanceChart,
            source = viewModel.pehBalance
        )
    }

    companion object {
        fun makeIntent(context: Context): Intent {
            return Intent(context, PehBalanceActivity::class.java)
        }
    }
}