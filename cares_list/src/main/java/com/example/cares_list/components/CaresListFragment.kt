package com.example.cares_list.components

import android.os.Bundle
import android.view.View
import com.example.cares_list.databinding.FragmentCaresListBinding
import com.example.corev2.ui.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CaresListFragment : BaseFragment<FragmentCaresListBinding>(
    bindingInflater = FragmentCaresListBinding::inflate
) {

    private val viewModel: CaresListViewModel by viewModel()
    private val caresAdapter: CaresAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeader()
        setupCaresRecycler()
    }

    private fun setupHeader() {
        viewModel.today.observe(viewLifecycleOwner) {
            binding.header.today = it
        }
        viewModel.daysFromLastCare.observe(viewLifecycleOwner) {
            binding.header.daysFromLastCare = it ?: 0
        }
    }

    private fun setupCaresRecycler() {
        binding.caresRecycler.adapter = caresAdapter
        caresAdapter.addSource(viewModel.orderedCares, viewLifecycleOwner)
    }
}