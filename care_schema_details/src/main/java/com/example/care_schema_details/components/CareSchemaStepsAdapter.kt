package com.example.care_schema_details.components

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.care_schema_details.CareSchemaDetailsActivity
import com.example.care_schema_details.R
import com.example.care_schema_details.databinding.ItemCareSchemaStepBinding
import com.example.common.base.BaseRecyclerAdapter
import com.example.common.base.BaseViewHolder
import com.example.common.binding.BindingRecyclerAdapter
import com.example.common.binding.BindingViewHolder
import com.example.common.binding.Converter
import com.example.common.extensions.visibleOrGone
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep
import java.lang.ref.WeakReference
import java.util.*

internal class CareSchemaStepsAdapter(
    boundActivity: CareSchemaDetailsActivity,
    private val viewModel: CareSchemaDetailsViewModel,
    private val careSchemaStepsTouchHelperCallback: CareSchemaStepsTouchHelperCallback
) : BaseRecyclerAdapter<CareSchemaStep, ItemCareSchemaStepBinding>() {

    private val boundActivityRef = WeakReference(boundActivity)
    private val _noSteps = MutableLiveData(true)

    val noSteps: LiveData<Boolean> = _noSteps

    val touchHelper: ItemTouchHelper by lazy {
        careSchemaStepsTouchHelperCallback.setOnMoveListener(this::moveCareProduct)
        ItemTouchHelper(careSchemaStepsTouchHelperCallback)
    }

    init {
        observeStepsEditModeEnabled()
    }

    fun addStep(type: CareStep.Type) {
        val newStepPosition = itemCount
        val newStep = CareSchemaStep(type, newStepPosition)
        itemsList.add(newStep)
        notifyItemInserted(newStepPosition)
        onStepsNumberChanged()
    }

    fun removeStep(position: Int) {
        itemsList.removeAt(position)
        notifyItemRemoved(position)
        onStepsNumberChanged()
    }

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

    private fun observeStepsEditModeEnabled() {
        viewModel.stepsEditModeEnabled.observe(boundActivityRef.get()!!) {
            notifyItemRangeChanged(0, itemsList.size)
        }
    }

    private fun moveCareProduct(fromPosition: Int, toPosition: Int) {
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
        updateStepsOrder()
        notifyItemChanged(fromPosition)
        notifyItemChanged(toPosition)
    }

    private fun onStepsNumberChanged() {
        updateStepsOrder()
        updateNoSteps()
    }

    private fun updateStepsOrder() {
        itemsList.forEachIndexed { index, careSchemaStep ->
            careSchemaStep.order = index
        }
    }

    private fun updateNoSteps() {
        _noSteps.value = itemsList.isEmpty()
    }
}
