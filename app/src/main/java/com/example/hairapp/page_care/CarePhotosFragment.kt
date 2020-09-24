package com.example.hairapp.page_care

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.hairapp.R
import com.example.hairapp.framework.Binder
import com.example.hairapp.framework.RecyclerLiveAdapter
import kotlinx.android.synthetic.main.fragment_care_photos.*

class CarePhotosFragment : Fragment() {

    private val viewModel: CareViewModel by activityViewModels()

    fun displayPhoto(photo: String) {
        PhotoPreviewFragment(photo).show(childFragmentManager, "PhotoPreview")
    }

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

        care_photos_recycler.adapter = RecyclerLiveAdapter(
            controller = this,
            lifecycleOwner = viewLifecycleOwner,
            layoutResId = R.layout.item_care_photo,
            source = viewModel.photos
        )
    }
}