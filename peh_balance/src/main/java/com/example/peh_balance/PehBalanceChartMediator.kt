package com.example.peh_balance

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.example.corev2.entities.PehBalance
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class PehBalanceChartMediator(
    private val activity: AppCompatActivity,
    private val pieChart: PieChart,
    private val source: LiveData<PehBalance>
) {

    init {
        setupElements()
        setupInteractions()
        setupStyling()
        setupCenterText()
        subscribeDataSource()
    }

    private fun setupElements() = pieChart.run {
        legend.isEnabled = false
        description.isEnabled = false
    }

    private fun setupInteractions() = pieChart.run {
        setTouchEnabled(false)
        isDragDecelerationEnabled = false
        transparentCircleRadius = 0f
    }

    private fun setupStyling() = pieChart.run {
        setHoleColor(activity.getColor(R.color.color_primary))
        setUsePercentValues(true)
    }

    private fun setupCenterText() = pieChart.run {
        centerText = "PEH"
        setCenterTextColor(activity.getColor(R.color.color_primary_light))
        setCenterTextSize(48f)
    }

    private fun subscribeDataSource() {
        source.observe(activity) {
            pieChart.run {
                data = PieData(makeDataSet(it))
                animateXY(400, 400)
                invalidate()
            }
        }
    }

    private fun makeDataSet(pehBalance: PehBalance): PieDataSet {
        val entries = mutableListOf(
            PieEntry(pehBalance.proteins.toFloat(), "Proteiny"),
            PieEntry(pehBalance.emollients.toFloat(), "Emolienty"),
            PieEntry(pehBalance.humectants.toFloat(), "Humektanty")
        )
        val entriesColors = mutableListOf(
            activity.getColor(R.color.color_proteins),
            activity.getColor(R.color.color_emollients),
            activity.getColor(R.color.color_humectants)
        )
        indexesOfZeros(entries).forEach { idx ->
            entries.removeAt(idx)
            entriesColors.removeAt(idx)
        }
        return PieDataSet(entries, null).apply {
            colors = entriesColors
            sliceSpace = 8f
            valueFormatter = PehValueFormatter()
            valueTextColor = activity.getColor(R.color.color_white)
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
}