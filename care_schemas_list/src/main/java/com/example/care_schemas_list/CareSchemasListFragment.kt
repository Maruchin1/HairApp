package com.example.care_schemas_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.care_schemas_list.databinding.FragmentCareSchemasListBinding
import com.example.corev2.entities.CareSchema
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.DialogService
import com.example.corev2.ui.InflateBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareSchemasListFragment : BaseFragment<FragmentCareSchemasListBinding>(),
    SchemasAdapter.Handler {

    override val inflateBinding: InflateBinding<FragmentCareSchemasListBinding>
        get() = FragmentCareSchemasListBinding::inflate

    private val viewModel: CareSchemasListViewModel by viewModel()
    private val schemasAdapter: SchemasAdapter by inject()
    private val dialogService: DialogService by inject()

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