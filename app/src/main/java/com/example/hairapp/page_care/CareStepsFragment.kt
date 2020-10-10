package com.example.hairapp.page_care

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.core.domain.CareStep
import com.example.core.domain.Product
import com.example.hairapp.R
import com.example.hairapp.framework.Binder
import com.example.hairapp.framework.confirmDialog
import com.example.hairapp.page_select_product.SelectProductContract
import kotlinx.android.synthetic.main.fragment_care_steps.*
import kotlinx.android.synthetic.main.item_care_step.view.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CareStepsFragment : Fragment() {

    private val viewModel: CareViewModel by sharedViewModel()

    private val selectProductRequest = registerForActivityResult(SelectProductContract()) {
        lifecycleScope.launch {
            it.productId?.let { selectedProductId ->
                viewModel.findProduct(selectedProductId)
            }?.let { selectedProduct ->
                if (it.position == null) {
                    adapter.addStep(it.type, selectedProduct)
                } else {
                    adapter.setStepProduct(it.position, selectedProduct)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private val adapter: CareStepsAdapter = CareStepsAdapter(
        controller = this,
        layoutResId = R.layout.item_care_step,
    ).apply {
        setItemSetup { viewHolder, _ ->
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
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            adapter.moveCareProduct(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.removeStep(viewHolder.adapterPosition)
        }
    })

    fun addStep(type: CareStep.Type) {
        val input = SelectProductContract.Input(null, type)
        selectProductRequest.launch(input)
    }

    fun selectProduct(careStep: CareStep) {
        val input = SelectProductContract.Input(careStep.order, careStep.type)
        selectProductRequest.launch(input)
    }

    fun getSteps(): List<CareStep> {
        return adapter.getAllCareProducts()
    }

    fun deleteStep(careStep: CareStep) = requireActivity().confirmDialog(
        title = getString(R.string.confirm_delete),
        message = getString(R.string.care_activity_confirm_delete_step_message)
    ) {
        adapter.removeStep(careStep.order)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_care_steps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        care_steps_recycler.adapter = adapter
        touchHelper.attachToRecyclerView(care_steps_recycler)

        viewModel.stepsAvailable.observe(viewLifecycleOwner) {
            Binder.setVisibleOrGone(care_steps_recycler, it)
        }
        viewModel.steps.observe(viewLifecycleOwner) {
            TransitionManager.beginDelayedTransition(care_steps_recycler, ChangeBounds())
            adapter.updateItems(it)
        }
        viewModel.addProductsProportionSource(adapter.productsProportion)

        adapter.noSteps.observe(viewLifecycleOwner) {
            Binder.setVisibleOrGone(care_steps_not_steps, it)
            Binder.setVisibleOrGone(care_steps_recycler, !it)
        }
    }

}