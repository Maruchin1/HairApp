package com.example.hairapp.page_care

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.core.domain.CareStep
import com.example.hairapp.R
import com.example.hairapp.framework.Binder
import com.example.hairapp.framework.confirmDialog
import com.example.hairapp.page_select_product.SelectProductContract
import kotlinx.android.synthetic.main.fragment_care_steps.*
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
        dragHandleResId = R.id.item_care_product_edit_drag_handle
    )

    fun addStep(type: CareStep.Type) {
        val input = SelectProductContract.Input(null, type)
        selectProductRequest.launch(input)
    }

    fun selectProduct(careStep: CareStep) {
        val input = SelectProductContract.Input(careStep.order, careStep.type)
        selectProductRequest.launch(input)
    }

    fun getSteps(): List<CareStep> {
        return adapter.getAllCareSteps()
    }

    fun deleteStep(careStep: CareStep) = lifecycleScope.launch {
        val confirmed = requireActivity().confirmDialog(
            title = getString(R.string.confirm_delete),
            message = getString(R.string.care_activity_confirm_delete_step_message)
        )
        if (confirmed) {
            adapter.removeStep(careStep.order)
        }
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
        adapter.touchHelper.attachToRecyclerView(care_steps_recycler)

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