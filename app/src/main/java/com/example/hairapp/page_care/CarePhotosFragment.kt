package com.example.hairapp.page_care

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hairapp.R
import com.example.hairapp.common.CarePhotoItemController
import com.example.hairapp.common.PhotoPreviewDialog
import com.example.hairapp.framework.Binder
import com.example.hairapp.framework.BindingRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_care_photos.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CarePhotosFragment : Fragment(), CarePhotoItemController {

    private val viewModel: CareViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_care_photos, container, false)
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
        PhotoPreviewDialog(
            photo = data,
            onPhotoDelete = { viewModel.deletePhoto(it) }
        ).show(childFragmentManager, "PhotoPreview")
    }
}