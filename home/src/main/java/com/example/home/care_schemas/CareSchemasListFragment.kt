package com.example.home.care_schemas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.base.BaseFragment
import com.example.common.navigation.AppNavigator
import com.example.common.navigation.CareSchemaDetailsDestination
import com.example.core.domain.CareSchema
import com.example.home.databinding.FragmentCareSchemasListBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CareSchemasListFragment : BaseFragment<FragmentCareSchemasListBinding>() {

    private val viewModel: CareSchemasListViewModel by viewModel()
    private val schemasAdapter: SchemasAdapter by inject { parametersOf(this::openSchemaDetails) }
    private val appNavigator: AppNavigator by inject()

    override fun bindLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCareSchemasListBinding {
        return FragmentCareSchemasListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSchemasRecycler()
    }

    private fun setupSchemasRecycler() {
        binding.schemasRecycler.adapter = schemasAdapter
        schemasAdapter.setItemComparator { oldItem, newItem -> oldItem.id == newItem.id }
        schemasAdapter.addSource(viewModel.careSchemas, viewLifecycleOwner)
    }

    private fun openSchemaDetails(schema: CareSchema) {
        appNavigator.toDestination(
            originActivity = requireActivity(),
            destination = CareSchemaDetailsDestination(schema.id)
        )
    }
}