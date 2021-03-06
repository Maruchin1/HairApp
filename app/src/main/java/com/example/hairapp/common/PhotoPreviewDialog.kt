package com.example.hairapp.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.databinding.DialogPhotoPreviewBinding
import com.example.hairapp.framework.bind
import com.example.hairapp.framework.confirmDialog
import com.example.hairapp.framework.setSystemColors
import kotlinx.android.synthetic.main.dialog_photo_preview.*
import kotlinx.coroutines.launch

class PhotoPreviewDialog(
    photo: String,
    private val onPhotoDelete: ((String) -> Unit)?
) : DialogFragment() {

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
        requireActivity().setSystemColors(R.color.color_primary)
        return bind<DialogPhotoPreviewBinding>(
            inflater,
            container,
            R.layout.dialog_photo_preview,
            null
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

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().setSystemColors(R.color.color_primary)
    }

    private fun deletePhoto() = lifecycleScope.launch {
        val confirmed = requireActivity().confirmDialog(
            title = "Usuń zdjęcie",
            message = "Czy chcesz usunąć wybrane zdjęcie z pielęgnacji? Tej operacji nie będzie można cofnąć."
        )
        if (confirmed) {
            _photo.value?.let { onPhotoDelete?.invoke(it) }
            dismiss()
        }
    }
}