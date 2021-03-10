package com.example.hairapp.page_care

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hairapp.R
import com.example.hairapp.databinding.FragmentCareNotesBinding
import com.example.hairapp.framework.Animator
import com.example.hairapp.framework.bindActivity
import com.example.hairapp.framework.bindFragment
import kotlinx.android.synthetic.main.fragment_care_notes.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CareNotesFragment : Fragment() {

    private val viewModel: CareViewModel by sharedViewModel()
    private val _mode = MutableLiveData(Mode.VIEW)

    val mode: LiveData<Mode> = _mode

    fun editNotes() {
        changeMode(Mode.EDIT)
    }

    fun discardNotes() {
        changeMode(Mode.VIEW)
    }

    fun saveNotes() {
        changeMode(Mode.VIEW)
        val newNotes = edit_notes.text.toString()
        viewModel.updateNotes(newNotes)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindFragment<FragmentCareNotesBinding>(
            inflater, container, R.layout.fragment_care_notes, viewModel
        ).root
    }

    private fun changeMode(newMode: Mode) {
        Animator.fastFade(container)
        _mode.value = newMode
    }

    enum class Mode {
        VIEW, EDIT
    }
}