package com.example.hairapp.page_care_schemas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.common.navigation.AppNavigator
import com.example.common.navigation.CareSchemaDetailsDestination
import com.example.core.domain.CareSchema
import com.example.hairapp.R
import com.example.hairapp.databinding.FragmentCareSchemasBinding
import com.example.hairapp.framework.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareSchemasFragment : Fragment() {

    private val viewModel: CareSchemasViewModel by viewModel()
    private val dialog: Dialog by inject()
    private val appNavigator: AppNavigator by inject()

    private val adapter by lazy {
        BindingRecyclerAdapter<CareSchema>(this, R.layout.item_care_schema)
    }

    private var binding: FragmentCareSchemasBinding? = null

    fun addNewSchema() = lifecycleScope.launch {
        dialog.typeText(
            context = requireContext(),
            title = getString(R.string.name_your_schema)
        )?.let { name ->
            viewModel.addCareSchema(name)
                .onSuccess { openSchemaPage(it) }
                .onFailure { Snackbar.error(requireActivity(), it) }
        }
    }

    fun openSchemaPage(schemaId: Int) {
        appNavigator.toDestination(
            originActivity = requireActivity(),
            destination = CareSchemaDetailsDestination(schemaId)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindFragment(inflater, container, R.layout.fragment_care_schemas, viewModel)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.careSchemasRecycler?.adapter = adapter
        adapter.setSource(viewModel.careSchemas, this)
    }
}