package com.example.hairapp.page_care

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hairapp.R
import com.example.hairapp.common.CarePhotoItemController
import com.example.hairapp.databinding.FragmentCarePhotosBinding
import com.example.hairapp.framework.Binder
import com.example.hairapp.framework.BindingRecyclerAdapter
import com.example.hairapp.framework.bindFragment
import com.example.hairapp.page_photo_preview.PhotoPreviewActivity
import kotlinx.android.synthetic.main.fragment_care_photos.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CarePhotosFragment : Fragment(), CarePhotoItemController {

    private val viewModel: CareViewModel by sharedViewModel()

    private val openPhoto = registerForActivityResult(
        PhotoPreviewActivity.Contract()
    ) { photoToDelete ->
        photoToDelete?.let {
            viewModel.deletePhoto(it)
        }
    }

    private var binding: FragmentCarePhotosBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindFragment(inflater, container, R.layout.fragment_cares_list, viewModel)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        care_photos_recycler.adapter = BindingRecyclerAdapter<String>(
            controller = this,
            layoutResId = R.layout.item_care_photo
        ).apply {
            setSource(viewModel.photos, viewLifecycleOwner)
        }

        viewModel.noPhotos.observe(viewLifecycleOwner) {
            Binder.setVisibleOrGone(care_photos_no_photos, it)
            Binder.setVisibleOrGone(care_photos_recycler, !it)
        }
    }

    override fun onPhotoSelected(data: String) {
        openPhoto.launch(data)
    }
}