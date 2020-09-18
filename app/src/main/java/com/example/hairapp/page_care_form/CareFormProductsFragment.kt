package com.example.hairapp.page_care_form

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.CareProduct
import com.example.core.domain.ProductApplication
import com.example.hairapp.R
import com.example.hairapp.framework.RecyclerAdapter
import com.example.hairapp.page_select_product.SelectProductActivity
import com.example.hairapp.page_select_product.SelectProductContract
import kotlinx.android.synthetic.main.fragment_products_list.recycler
import kotlinx.android.synthetic.main.item_care_product_edit.view.*
import kotlinx.coroutines.launch
import java.util.*

class CareFormProductsFragment : Fragment() {

    private val viewModel: CareFormViewModel by activityViewModels()

    private val selectProductRequest = registerForActivityResult(SelectProductContract()) {
        val requestedCareProduct = it.first
        val selectedProductId = it.second
        if (selectedProductId != null) {
            setSelectedProduct(requestedCareProduct, selectedProductId)
        }
    }


    fun addProduct() {
        val newCareProduct = CareProduct()
        adapter.addItem(newCareProduct)
    }

    fun selectProduct(careProduct: CareProduct) {
        selectProductRequest.launch(careProduct)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_care_form_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.steps.observe(viewLifecycleOwner) {
            adapter.updateItems(it)
        }

        recycler.adapter = adapter
        touchHelper.attachToRecyclerView(recycler)
    }

    @SuppressLint("ClickableViewAccessibility")
    private val adapter: RecyclerAdapter<CareProduct> = RecyclerAdapter.build<CareProduct>(
        fragment = this,
        layoutResId = R.layout.item_care_product_edit
    ).withItemSetup { viewHolder, _ ->
        viewHolder.itemView.item_care_product_edit_drag_handle.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN)
                touchHelper.startDrag(viewHolder)
            false
        }
    }

    private val touchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
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
            val careProduct = adapter.getItem(viewHolder.adapterPosition)
            if (careProduct == null || careProduct.specificApplicationType != null)
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
            adapter.moveItem(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.removeItem(viewHolder.adapterPosition)
        }

        override fun canDropOver(
            recyclerView: RecyclerView,
            current: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val targetItem = adapter.getItem(target.adapterPosition)
            if (targetItem?.specificApplicationType == null) {
                return true
            }

            val currentPosition = current.adapterPosition
            val targetPosition = target.adapterPosition

            return if (currentPosition < targetPosition) {
                val nextItem = adapter.getItem(targetPosition + 1)
                nextItem?.specificApplicationType == null
            } else {
                val previousItem = adapter.getItem(targetPosition - 1)
                previousItem?.specificApplicationType == null
            }
        }
    })

    private fun setSelectedProduct(
        careProduct: CareProduct,
        selectedProductId: Int
    ) = lifecycleScope.launch {

        val selectedProduct = viewModel.findProduct(selectedProductId)
        val itemPosition = adapter.getItemPosition(careProduct)

        careProduct.product = selectedProduct
        adapter.updateSingleItem(careProduct, itemPosition)
    }

}