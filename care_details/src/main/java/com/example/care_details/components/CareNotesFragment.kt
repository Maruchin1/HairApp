package com.example.care_details.components

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import com.example.care_details.R
import com.example.care_details.databinding.FragmentCareNotesBinding
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.setSource
import com.example.corev2.ui.setVisibleOrGone
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class CareNotesFragment : BaseFragment<FragmentCareNotesBinding>(
    bindingInflater = FragmentCareNotesBinding::inflate
) {

    private val viewModel: CareDetailsViewModel by sharedViewModel()
    private val careDetailsActivity: CareDetailsActivity
        get() = requireActivity() as CareDetailsActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNotes()
        observeEditMode()
        observeNotesNotAvailable()
    }

    private fun setupNotes() {
        binding.apply {
            notes.setSource(viewModel.notes, viewLifecycleOwner)
            noNotes.apply {
                icon.setImageResource(R.drawable.ic_round_notes_24)
                message.text = getString(R.string.care_notes_hint)
            }
            notesInput.setSource(viewModel.notes, viewLifecycleOwner)
        }
    }

    private fun observeEditMode() = viewModel.notesEditMode.observe(viewLifecycleOwner) {
        TransitionManager.beginDelayedTransition(careDetailsActivity.binding.root)
        updateNotesVisibility(it)
    }

    private fun updateNotesVisibility(isEditMode: Boolean) {
        binding.apply {
            notesCard.setVisibleOrGone(!isEditMode)
            notesInputCard.setVisibleOrGone(isEditMode)
        }
    }

    private fun observeNotesNotAvailable() =
        viewModel.notesNotAvailable.observe(viewLifecycleOwner) {
            binding.noNotes.root.setVisibleOrGone(it)
        }
}