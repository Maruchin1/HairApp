package com.example.hairapp.page_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.core.domain.Care
import com.example.hairapp.R
import com.example.hairapp.common.CareItemController
import com.example.hairapp.framework.Binder
import com.example.hairapp.framework.BindingRecyclerAdapter
import com.example.hairapp.page_care.CareActivity
import kotlinx.android.synthetic.main.fragment_care_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CareListFragment : Fragment(), CareItemController {

    private val viewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_care_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_cares.adapter = BindingRecyclerAdapter<Care>(
            controller = this,
            layoutResId = R.layout.item_care,
        ).apply {
            setSource(viewModel.cares, viewLifecycleOwner)
            setItemComparator { it.id }
        }

        viewModel.noCares.observe(viewLifecycleOwner) {
            Binder.setVisibleOrGone(recycler_cares, !it)
            Binder.setVisibleOrGone(no_cares, it)
        }
    }

    override fun onCareSelected(care: Care) {
        val intent = CareActivity.makeIntent(requireContext(), care.id)
        startActivity(intent)
    }
}