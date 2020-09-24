package com.example.hairapp.page_care

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hairapp.R
import com.example.hairapp.databinding.FragmentPhotoPreviewBinding
import com.example.hairapp.framework.bind
import com.example.hairapp.framework.confirmDialog
import kotlinx.android.synthetic.main.fragment_photo_preview.*

class PhotoPreviewFragment(photo: String) : DialogFragment() {

    private val viewModel: CareViewModel by activityViewModels()
    private val _photo = MutableLiveData(photo)

    val photo: LiveData<String> = _photo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bind<FragmentPhotoPreviewBinding>(
            inflater,
            container,
            R.layout.fragment_photo_preview,
            viewModel
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_delete -> deletePhoto()
            }
            true
        }
    }

    private fun deletePhoto() = requireActivity().confirmDialog(
        title = "Usuń zdjęcie",
        message = "Czy chcesz usunąć wybrane zdjęcie z pielęgnacji? Tej operacji nie będzie można cofnąć."
    ) {
        _photo.value?.let { viewModel.deletePhoto(it) }
        dismiss()
    }
}