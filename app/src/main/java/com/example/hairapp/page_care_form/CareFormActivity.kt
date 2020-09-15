package com.example.hairapp.page_care_form

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.CareProduct
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityCareFormBinding
import com.example.hairapp.framework.bind
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_care_form.*
import kotlinx.android.synthetic.main.item_care_product_extra_edit.view.*
import java.util.*

@AndroidEntryPoint
class CareFormActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA = 0
        private const val MAIN = 1
    }

    private val viewModel: CareFormViewModel by viewModels()
    private val recyclerItems = mutableListOf<CareProduct>()
    private val recyclerAdapter = ProductsAdapter()
    private val touchHelper = ItemTouchHelper(TouchCallback())

    fun selectDate() {
        MaterialDatePicker.Builder
            .datePicker()
            .build()
            .show(supportFragmentManager, "DatePicker")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityCareFormBinding>(R.layout.activity_care_form, viewModel)

        input_date.inputType = 0
        input_care_method.inputType = 0

        val care = viewModel.defaultCare
        recyclerItems.run {
            addAll(care.before)
            addAll(care.main)
            addAll(care.after)
        }

        recycler.adapter = recyclerAdapter
        touchHelper.attachToRecyclerView(recycler)
    }

    inner class ProductsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                EXTRA -> {
                    val view =
                        inflater.inflate(R.layout.item_care_product_extra_edit, parent, false)
                    ExtraProductViewHolder(view)
                }
                MAIN -> {
                    val view = inflater.inflate(R.layout.item_care_product_main_edit, parent, false)
                    MainProductViewHolder(view)
                }
                else -> throw IllegalStateException()
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        }

        override fun getItemCount(): Int {
            return recyclerItems.size
        }

        override fun getItemViewType(position: Int): Int {
            return when (recyclerItems[position]) {
                is CareProduct.Extra -> EXTRA
                is CareProduct.Main -> MAIN
            }
        }
    }

    inner class TouchCallback : ItemTouchHelper.Callback() {
        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            if (viewHolder is MainProductViewHolder)
                return 0
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            if (fromPosition < toPosition) {
                for (i in fromPosition until toPosition) {
                    Collections.swap(recyclerItems, i, i + 1)
                }
            } else {
                for (i in fromPosition downTo toPosition + 1) {
                    Collections.swap(recyclerItems, i, i - 1)
                }
            }
            recyclerAdapter.notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            recyclerItems.removeAt(viewHolder.adapterPosition)
            recyclerAdapter.notifyItemRemoved(viewHolder.adapterPosition)
        }

        override fun canDropOver(
            recyclerView: RecyclerView,
            current: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = when {
            target is ExtraProductViewHolder -> true
            current.adapterPosition < target.adapterPosition -> {
                val nextItem = recyclerItems[target.adapterPosition + 1]
                nextItem is CareProduct.Extra
            }
            else -> {
                val previousItem = recyclerItems[target.adapterPosition - 1]
                previousItem is CareProduct.Extra
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    inner class ExtraProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.item_care_product_edit_drag_handle.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN)
                    touchHelper.startDrag(this)
                false
            }
        }
    }

    inner class MainProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}