package com.example.hairapp.page_cares_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.core.domain.Care
import com.example.hairapp.R
import com.example.hairapp.common.CareItemController
import com.example.hairapp.databinding.FragmentCaresListBinding
import com.example.hairapp.framework.Binder
import com.example.hairapp.framework.BindingRecyclerAdapter
import com.example.hairapp.framework.Dialog
import com.example.hairapp.framework.bindFragment
import com.example.hairapp.page_care.CareActivity
import com.example.hairapp.page_peh_balance.PehBalanceActivity
import com.example.hairapp.page_photos_gallery.PhotosGalleryActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CaresListFragment : Fragment(), CareItemController {

    private val viewModel: CaresListViewModel by viewModel()
    private val binding: FragmentCaresListBinding
        get() = _binding!!

    private var _binding: FragmentCaresListBinding? = null

    fun openStatistics() {
        startActivity(PehBalanceActivity.makeIntent(requireContext()))
    }

    fun openPhotos() {
        startActivity(PhotosGalleryActivity.makeIntent(requireContext()))
    }

    fun addNewCare() = lifecycleScope.launch {
        Dialog.selectCareSchema(
            context = requireContext(),
            schemas = viewModel.getCareSchemas()
        )?.let { selectedSchema ->
            startActivity(CareActivity.makeIntent(requireContext(), selectedSchema))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindFragment(inflater, container, R.layout.fragment_cares_list, viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerCares.adapter = BindingRecyclerAdapter<Care>(
            controller = this,
            layoutResId = R.layout.item_care,
        ).apply {
            setSource(viewModel.orderedCares, viewLifecycleOwner)
            setItemComparator { it.id }
        }

        viewModel.noCares.observe(viewLifecycleOwner) {
            Binder.setVisibleOrGone(binding.recyclerCares, !it)
            Binder.setVisibleOrGone(binding.noCares, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCareSelected(care: Care) {
        val intent = CareActivity.makeIntent(requireContext(), care.id)
        startActivity(intent)
    }
}