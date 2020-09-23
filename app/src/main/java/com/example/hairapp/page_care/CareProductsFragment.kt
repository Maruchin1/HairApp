package com.example.hairapp.page_care

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.CareProduct
import com.example.hairapp.R
import com.example.hairapp.framework.Binder
import com.example.hairapp.page_select_product.SelectProductContract
import kotlinx.android.synthetic.main.fragment_care_products.*
import kotlinx.android.synthetic.main.fragment_products_list.recycler
import kotlinx.android.synthetic.main.item_care_product_edit.view.*
import kotlinx.coroutines.launch

class CareProductsFragment : Fragment() {

    private val viewModel: CareViewModel by activityViewModels()

    private val selectProductRequest = registerForActivityResult(SelectProductContract()) {
        val (requestedCareProduct, selectedProductId) = it
        lifecycleScope.launch {
            selectedProductId?.let { selectedProductId ->
                viewModel.findProduct(selectedProductId)
            }?.let { selectedProduct ->
                adapter.setProduct(requestedCareProduct, selectedProduct)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private val adapter: CareProductsAdapter = CareProductsAdapter(
        controller = this,
        layoutResId = R.layout.item_care_product_edit,
    ).apply {
        withItemSetup { viewHolder, _ ->
            viewHolder.itemView.item_care_product_edit_drag_handle.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN)
                    touchHelper.startDrag(viewHolder)
                false
            }
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
            val careProduct = adapter.getCareProduct(viewHolder.adapterPosition)
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
            adapter.moveCareProduct(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.removeCareProduct(viewHolder.adapterPosition)
        }

        override fun canDropOver(
            recyclerView: RecyclerView,
            current: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val targetItem = adapter.getCareProduct(target.adapterPosition)
            if (targetItem?.specificApplicationType == null) {
                return true
            }

            val currentPosition = current.adapterPosition
            val targetPosition = target.adapterPosition

            return if (currentPosition < targetPosition) {
                val nextItem = adapter.getCareProduct(targetPosition + 1)
                nextItem?.specificApplicationType == null
            } else {
                val previousItem = adapter.getCareProduct(targetPosition - 1)
                previousItem?.specificApplicationType == null
            }
        }
    })

    fun addProduct() {
        adapter.addCareProduct()
    }

    fun selectProduct(careProduct: CareProduct) {
        selectProductRequest.launch(careProduct)
    }

    fun getCareProducts(): List<CareProduct> {
        return adapter.getAllCareProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_care_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        care_products_recycler.adapter = adapter
        touchHelper.attachToRecyclerView(recycler)

        viewModel.stepsAvailable.observe(viewLifecycleOwner) {
            Binder.setVisibleOrGone(care_products_header, it)
            Binder.setVisibleOrGone(care_products_recycler, it)
        }
        viewModel.steps.observe(viewLifecycleOwner) {
            adapter.updateItems(it)
        }
        viewModel.addProductsProportionSource(adapter.productsProportion)
    }

}