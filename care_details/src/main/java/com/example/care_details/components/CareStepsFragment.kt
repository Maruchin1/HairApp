package com.example.care_details.components

import android.os.Bundle
import android.view.View
import com.example.care_details.databinding.FragmentCareStepsBinding
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.setTitleSource
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class CareStepsFragment : BaseFragment<FragmentCareStepsBinding>(
    bindingInflater = FragmentCareStepsBinding::inflate
) {

    private val viewModel: CareDetailsViewModel by sharedViewModel()
    private val adapter: CareStepsAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupStepsRecycler()
    }

    private fun setupToolbar() {
        binding.toolbar.setTitleSource(viewModel.schemaName, viewLifecycleOwner)
    }

    private fun setupStepsRecycler() {
        binding.stepsRecycler.adapter = adapter
        adapter.addSource(viewModel.steps, viewLifecycleOwner)
        adapter.setItemComparator { oldItem, newItem -> oldItem.careStep.id == newItem.careStep.id }
    }
}