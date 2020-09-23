package com.example.hairapp.page_care

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.hairapp.R
import com.example.hairapp.framework.Binder
import kotlinx.android.synthetic.main.fragment_care_photos.*

class CarePhotosFragment : Fragment() {

    private val viewModel: CareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_care_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.photosAvailable.observe(viewLifecycleOwner) {
            Binder.setVisibleOrGone(care_photos_header, it)
            Binder.setVisibleOrGone(care_photos_recycler, it)
        }
    }
}