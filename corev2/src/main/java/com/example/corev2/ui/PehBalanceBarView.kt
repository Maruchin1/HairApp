package com.example.corev2.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.corev2.databinding.ViewPehBalanceBarBinding
import com.example.corev2.entities.PehBalance

class PehBalanceBarView(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs) {

    private val binding = bindLayout(ViewPehBalanceBarBinding::inflate)

    var pehBalance: PehBalance? = null
        set(value) {
            field = value
            if (value != null) {
                applyIngredientsWeights(value)
            }
            updateLabelVisibility(value)
        }

    private fun applyIngredientsWeights(pehBalance: PehBalance) {
        binding.apply {
            proteins.setWeight(pehBalance.proteins)
            emollients.setWeight(pehBalance.emollients)
            humectants.setWeight(pehBalance.humectants)
        }
    }

    private fun updateLabelVisibility(pehBalance: PehBalance?) {
        val showLabel = pehBalance == null || pehBalance.isEmpty
        binding.label.setVisibleOrGone(showLabel)
    }
}