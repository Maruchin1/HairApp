package com.example.hairapp.page_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.core.domain.Care
import com.example.hairapp.R
import com.example.hairapp.common.CareItemController
import com.example.hairapp.framework.RecyclerLiveAdapter
import com.example.hairapp.page_care.CareActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_care_list.*

@AndroidEntryPoint
class CareListFragment : Fragment(), CareItemController {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_care_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = RecyclerLiveAdapter<Care>(
            controller = this,
            lifecycleOwner = this,
            layoutResId = R.layout.item_care,
            source = viewModel.cares
        ).withItemComparator { it.id }
    }

    override fun onCareSelected(care: Care) {
        val intent = CareActivity.makeIntent(requireContext(), care.id)
        startActivity(intent)
    }
}