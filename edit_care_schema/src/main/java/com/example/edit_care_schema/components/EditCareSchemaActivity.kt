package com.example.edit_care_schema.components

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.ui.BaseActivity
import com.example.corev2.ui.InflateActivityBinding
import com.example.corev2.ui.SystemColors
import com.example.corev2.ui.setVisibleOrGoneSource
import com.example.edit_care_schema.R
import com.example.edit_care_schema.databinding.ActivityEditCareSchemaBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class EditCareSchemaActivity : BaseActivity<ActivityEditCareSchemaBinding>(
    bindingInflater = ActivityEditCareSchemaBinding::inflate
),
    CareSchemaStepsAdapter.Handler {

    private val careSchemaId: Long
        get() = intent.getLongExtra(CARE_SCHEMA_ID, -1)

    private val viewModel: EditCareSchemaViewModel by viewModel()
    private val stepsAdapter: CareSchemaStepsAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.selectSchema(careSchemaId)
        }
        setupToolbar()
        setupStepsRecycler()
        setupNoStepsInfo()
        setupAddStepFab()
    }

    override fun onPause() {
        super.onPause()
        saveStepsIfOrderChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.apply {
            lightStatusBar()
            lightNavigationBar()
        }
    }

    override fun onStepLongClick(step: CareSchemaStep) {
        lifecycleScope.launch {
            viewModel.deleteStep(this@EditCareSchemaActivity, step)
        }
    }

    private fun setupToolbar() {
        viewModel.schemaName.observe(this) {
            binding.toolbar.title = it
        }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_change_schema_name -> changeSchemaName()
                R.id.action_delete_schema -> deleteSchema()
            }
            true
        }
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun changeSchemaName() = lifecycleScope.launch {
        viewModel.changeSchemaName(this@EditCareSchemaActivity)
    }

    private fun deleteSchema() = lifecycleScope.launch {
        viewModel.deleteSchema(this@EditCareSchemaActivity).fold(
            ifLeft = {},
            ifRight = { finish() }
        )
    }

    private fun setupStepsRecycler() {
        stepsAdapter.handler = this
        binding.stepsRecycler.adapter = stepsAdapter
        stepsAdapter.touchHelper.attachToRecyclerView(binding.stepsRecycler)
        stepsAdapter.addSource(viewModel.schemaSteps, this)
    }

    private fun setupNoStepsInfo() {
        binding.noSteps.container.setVisibleOrGoneSource(viewModel.noSteps, this)
    }

    private fun setupAddStepFab() {
        binding.fabAddStep.setOnClickListener {
            addCareSchemaStep()
        }
    }

    private fun addCareSchemaStep() = lifecycleScope.launch {
        viewModel.addStep(this@EditCareSchemaActivity)
    }

    private fun saveStepsIfOrderChanged() {
        if (stepsAdapter.stepsOrderChanged) {
            lifecycleScope.launch {
                viewModel.updateSteps(stepsAdapter.currentSteps)
            }
        }
    }

    companion object {
        const val CARE_SCHEMA_ID = "care_schema_id"
    }


}