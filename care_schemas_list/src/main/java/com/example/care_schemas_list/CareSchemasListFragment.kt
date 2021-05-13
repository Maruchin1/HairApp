package com.example.care_schemas_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.care_schemas_list.databinding.FragmentCareSchemasListBinding
import com.example.common.base.BaseFeatureFragment
import com.example.core.domain.CareSchema
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CareSchemasListFragment : BaseFeatureFragment<FragmentCareSchemasListBinding>(
    careSchemasListModule
) {

    private val viewModel: CareSchemasListViewModel by viewModel()
    private val schemasAdapter: SchemasAdapter by inject { parametersOf(this::openSchemaDetails) }

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

    }
}