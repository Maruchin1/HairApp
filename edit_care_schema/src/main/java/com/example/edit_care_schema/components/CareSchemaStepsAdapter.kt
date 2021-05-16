package com.example.edit_care_schema.components

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.common.base.BaseRecyclerAdapter
import com.example.common.base.BaseViewHolder
import com.example.common.binding.Converter
import com.example.core.domain.CareSchemaStep
import com.example.edit_care_schema.databinding.ItemCareSchemaStepBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

internal class CareSchemaStepsAdapter(
    private val careSchemaStepsTouchHelperCallback: CareSchemaStepsTouchHelperCallback
) : BaseRecyclerAdapter<CareSchemaStep, ItemCareSchemaStepBinding>() {

    val touchHelper: ItemTouchHelper by lazy {
        careSchemaStepsTouchHelperCallback.setOnMoveListener(this::moveCareProduct)
        ItemTouchHelper(careSchemaStepsTouchHelperCallback)
    }

    val currentSteps: List<CareSchemaStep>
        get() = itemsList

    var stepsOrderChanged: Boolean = false
        private set

    override fun onBindItemView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemCareSchemaStepBinding {
        return ItemCareSchemaStepBinding.inflate(layoutInflater, parent, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemCareSchemaStepBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.binding.dragHandle.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                touchHelper.startDrag(holder)
            }
            false
        }
    }

    override fun onBindItemData(binding: ItemCareSchemaStepBinding, item: CareSchemaStep) {
        binding.run {
            stepNumber.text = (item.order + 1).toString()
            stepName.text = Converter.careStepType(item.type)
        }
    }

    private fun moveCareProduct(fromPosition: Int, toPosition: Int) {
        stepsOrderChanged = true
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(itemsList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(itemsList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        updateStepsOrderDelayed()
    }

    private fun updateStepsOrderDelayed() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(500)
            updateStepsOrder()
        }
    }

    private fun updateStepsOrder() {
        val changedPositions = mutableListOf<Int>()
        itemsList.forEachIndexed { index, careSchemaStep ->
            if (careSchemaStep.order != index) {
                careSchemaStep.order = index
                changedPositions.add(index)
            }
        }
        changedPositions.forEach { position ->
            notifyItemChanged(position)
        }
    }
}
