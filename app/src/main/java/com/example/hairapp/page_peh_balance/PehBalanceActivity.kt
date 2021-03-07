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

        viewModel.pehBalance.observe(this) { setupPehChart(it) }
    }

    private fun setupPehChart(pehBalance: PehBalance) {
        binding.pehBalanceChart.run {
            data = PieData(makeDataSet(pehBalance))
            legend.isEnabled = false
            description.isEnabled = false
            setTouchEnabled(false)
            isDragDecelerationEnabled = false
            transparentCircleRadius = 0f
            setHoleColor(getColor(R.color.color_primary))

            centerText = "PEH"
            setCenterTextColor(getColor(R.color.color_primary_light))
            setCenterTextSize(48f)

            setUsePercentValues(true)

            invalidate()
        }
    }

    private fun makeDataSet(pehBalance: PehBalance): PieDataSet {
        val entries = mutableListOf(
            PieEntry(pehBalance.proteins.toFloat(), "Proteiny"),
            PieEntry(pehBalance.emollients.toFloat(), "Emolienty"),
            PieEntry(pehBalance.humectants.toFloat(), "Humektanty")
        )
        val entriesColors = mutableListOf(
            getColor(R.color.color_proteins),
            getColor(R.color.color_emollients),
            getColor(R.color.color_humectants)
        )
        indexesOfZeros(entries).forEach { idx ->
            entries.removeAt(idx)
            entriesColors.removeAt(idx)
        }
        return PieDataSet(entries, null).apply {
            colors = entriesColors
            sliceSpace = 8f
            valueFormatter = PehValueFormatter()
            valueTextColor = this@PehBalanceActivity.getColor(R.color.color_white)
            valueTextSize = 24f
        }
    }

    private fun indexesOfZeros(entries: List<PieEntry>): List<Int> {
        return entries
            .filter { it.value == 0f }
            .map { entries.indexOf(it) }
    }

    private class PehValueFormatter : ValueFormatter() {
        override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
            val intValue = value.roundToInt()
            return "$intValue %"
        }
    }

    companion object {
        fun makeIntent(context: Context): Intent {
            return Intent(context, PehBalanceActivity::class.java)
        }
    }
}