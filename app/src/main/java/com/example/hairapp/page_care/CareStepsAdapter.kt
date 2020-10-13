package com.example.hairapp.page_care

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.CareStep
import com.example.core.domain.Product
import com.example.core.domain.ProductsProportion
import com.example.hairapp.framework.BindingRecyclerAdapter
import com.example.hairapp.framework.BindingViewHolder
import java.util.*

class CareStepsAdapter(
    controller: Any,
    layoutResId: Int,
    private val dragHandleResId: Int?
) : BindingRecyclerAdapter<CareStep>(controller, layoutResId) {

    private val _productsProportion = MutableLiveData<ProductsProportion>()
    private val _noSteps = MutableLiveData(true)

    val productsProportion: LiveData<ProductsProportion> = _productsProportion
    val noSteps: LiveData<Boolean> = _noSteps

    val touchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return false
        }

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            moveCareProduct(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit
    })

    fun getAllCareProducts(): List<CareStep> {
        return itemsList
    }

    fun addStep(type: CareStep.Type, product: Product?) {
        val newStepPosition = 0
        val newStep = CareStep(type, newStepPosition, product)
        itemsList.add(newStepPosition, newStep)
        notifyItemInserted(newStepPosition)
        updateProductsProportion()
        updateItemsOrder()
        updateNoItems()
    }

    fun removeStep(position: Int) {
        itemsList.removeAt(position)
        notifyItemRemoved(position)
        updateProductsProportion()
        updateItemsOrder()
        updateNoItems()
    }

    fun setStepProduct(position: Int, selectedProduct: Product) {
        val careStep = itemsList[position]
        careStep.product = selectedProduct
        itemsList.removeAt(position)
        itemsList.add(position, careStep)
        notifyItemChanged(position)
        updateProductsProportion()
    }

    override fun updateItems(newList: List<CareStep>?) {
        super.updateItems(newList)
        updateProductsProportion()
        updateItemsOrder()
        updateNoItems()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (dragHandleResId != null) {
            val dragHandle: ImageButton = holder.itemView.findViewById(dragHandleResId)
            dragHandle.setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    touchHelper.startDrag(holder)
                }
                false
            }
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
        updateItemsOrder()
    }

    private fun updateProductsProportion() {
        _productsProportion.value = ProductsProportion(itemsList)
    }

    private fun updateItemsOrder() {
        itemsList.forEachIndexed { index, careStep ->
            careStep.order = index
        }
    }

    private fun updateNoItems() {
        _noSteps.value = itemsList.isEmpty()
    }
}