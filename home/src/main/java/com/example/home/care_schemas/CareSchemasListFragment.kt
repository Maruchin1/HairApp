package com.example.home.care_schemas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.common.base.BaseFragment
import com.example.common.modals.AppDialog
import com.example.common.navigation.AppNavigator
import com.example.common.navigation.EditCareSchemaDestination
import com.example.core.domain.CareSchema
import com.example.home.R
import com.example.home.databinding.FragmentCareSchemasListBinding
import com.example.home.use_case.AddCareSchemaUseCase
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CareSchemasListFragment : BaseFragment<FragmentCareSchemasListBinding>() {

    private val viewModel: CareSchemasListViewModel by viewModel()
    private val schemasAdapter: SchemasAdapter by inject { parametersOf(this::openSchemaDetails) }
    private val appNavigator: AppNavigator by inject()
    private val appDialog: AppDialog by inject()
    private val addCareSchemaUseCase: AddCareSchemaUseCase by inject()

    override fun bindLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCareSchemasListBinding {
        return FragmentCareSchemasListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarMenu()
        setupSchemasRecycler()
    }

    private fun setupToolbarMenu() = binding.toolbar.setOnMenuItemClickListener {
        when (it.itemId) {
            R.id.action_add_schema -> addCareSchema()
            R.id.action_sort_schemas -> openSortSchemasModal()
        }
        true
    }

    private fun addCareSchema() = lifecycleScope.launch {
        appDialog.typeText(
            context = requireContext(),
            title = getString(R.string.new_schema_name)
        )?.let { newSchemaName ->
            addCareSchemaUseCase(newSchemaName)
        }
    }

    private fun openSortSchemasModal() {

    }

    private fun setupSchemasRecycler() {
        binding.schemasRecycler.adapter = schemasAdapter
        schemasAdapter.setItemComparator { oldItem, newItem -> oldItem.id == newItem.id }
        schemasAdapter.addSource(viewModel.careSchemas, viewLifecycleOwner)
    }

    private fun openSchemaDetails(schema: CareSchema) {
        appNavigator.toDestination(
            originActivity = requireActivity(),
            destination = EditCareSchemaDestination(schema.id)
        )
    }
}