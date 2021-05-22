package com.example.care_schemas_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.care_schemas_list.databinding.FragmentCareSchemasListBinding
import com.example.corev2.entities.CareSchema
import com.example.corev2.navigation.EditCareSchemaDestination
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.DialogService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CareSchemasListFragment : BaseFragment<FragmentCareSchemasListBinding>(),
    SchemasAdapter.Handler {

    private val viewModel: CareSchemasListViewModel by viewModels()

    @Inject
    internal lateinit var schemasAdapter: SchemasAdapter

    @Inject
    internal lateinit var dialogService: DialogService

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

    override fun onCareSchemaClicked(careSchema: CareSchema) {
        viewModel.openSchema(requireActivity(), careSchema.id)
    }

    private fun setupToolbarMenu() = binding.toolbar.setOnMenuItemClickListener {
        when (it.itemId) {
            R.id.action_add_schema -> addCareSchema()
            R.id.action_sort_schemas -> openSortSchemasModal()
        }
        true
    }

    private fun addCareSchema() = lifecycleScope.launch {
        dialogService.typeText(
            context = requireContext(),
            title = getString(R.string.new_schema_name)
        )?.let { newSchemaName ->
            viewModel.addNewSchema(newSchemaName)
        }
    }

    private fun openSortSchemasModal() {

    }

    private fun setupSchemasRecycler() {
        schemasAdapter.handler = this
        binding.schemasRecycler.adapter = schemasAdapter
        schemasAdapter.setItemComparator { oldItem, newItem ->
            oldItem.careSchema.id == newItem.careSchema.id
        }
        schemasAdapter.addSource(viewModel.careSchemas, viewLifecycleOwner)
    }
}