package com.example.care_schema_details.components

import com.example.care_schema_details.CareSchemaDetailsActivity
import com.example.care_schema_details.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.ref.WeakReference

internal class FabAdapter(
    boundActivity: CareSchemaDetailsActivity,
    private val viewModel: CareSchemaDetailsViewModel
) {

    private val boundActivityRef = WeakReference(boundActivity)

    private val boundActivity: CareSchemaDetailsActivity
        get() = boundActivityRef.get()!!

    private val fab: FloatingActionButton
        get() = boundActivity.binding.editStepsFab

    init {
        observeStepsEditMode()
        setupClickListener()
    }

    private fun observeStepsEditMode() {
        viewModel.stepsEditModeEnabled.observe(boundActivity) { isStepsEditMode ->
            changeIcon(isStepsEditMode)
        }
    }

    private fun changeIcon(isStepsEdiMode: Boolean) {
        val iconResId = if (isStepsEdiMode) {
            R.drawable.ic_round_save_24
        } else {
            R.drawable.ic_round_edit_24
        }
        fab.hide()
        fab.setImageResource(iconResId)
        fab.show()
    }

    private fun setupClickListener() = fab.setOnClickListener {
        if (viewModel.isStepsEditModeEnabled()) {
            viewModel.saveStepsChanges()
        } else {
            viewModel.enableStepsEditMode()
        }
    }

}