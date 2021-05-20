package com.example.edit_care_schema

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

typealias OnMoveListener = (fromPosition: Int, toPosition: Int) -> Unit

class CareSchemaStepsTouchHelperCallback @Inject constructor() : ItemTouchHelper.Callback() {

    private var onMoveListener: OnMoveListener? = null

    fun setOnMoveListener(listener: OnMoveListener) {
        onMoveListener = listener
    }

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
        onMoveListener?.invoke(fromPosition, toPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit
}