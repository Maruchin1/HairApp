package com.example.edit_care_schema

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.common.base.BaseFeatureActivity
import com.example.common.base.SystemColors
import com.example.common.binding.Converter
import com.example.common.extensions.visibleOrGone
import com.example.common.modals.AppDialog
import com.example.core.domain.CareSchemaStep
import com.example.edit_care_schema.databinding.ActivityCareSchemaDetailsBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditCareSchemaActivity : BaseFeatureActivity<ActivityCareSchemaDetailsBinding>(
    featureModule = careSchemaDetailsModule,
), CareSchemaStepsAdapter.Callback {

    private val careSchemaId: Int
        get() = intent.getIntExtra(CARE_SCHEMA_ID, -1)

    private val viewModel: EditCareSchemaViewModel by viewModel { parametersOf(careSchemaId) }
    private val appDialog: AppDialog by inject()
    private val stepsAdapter: CareSchemaStepsAdapter by inject { parametersOf(this) }

    override fun bindActivity(): ActivityCareSchemaDetailsBinding {
        return ActivityCareSchemaDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
        setupStepsRecycler()
        setupNoStepsInfo()
        setupAddStepFab()
    }

    override fun onPause() {
        super.onPause()
        saveStepsIfOrderChanged()
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.apply {
            lightStatusBar()
            lightNavigationBar()
        }
    }

    override fun deleteSchemaStep(step: CareSchemaStep) {
        lifecycleScope.launch {
            appDialog.confirm(
                context = this@EditCareSchemaActivity,
                title = "Usunąć krok ${step.order + 1} ${Converter.careStepType(step.type)}?"
            ).let { confirmed ->
                if (confirmed) {
                    viewModel.deleteSchemaStep(careSchemaId, step.id)
                }
            }
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
    }

    private fun changeSchemaName() = lifecycleScope.launch {
        appDialog.typeText(
            context = this@EditCareSchemaActivity,
            title = getString(R.string.change_care_schema_name),
            currentValue = viewModel.getSchemaName()
        )?.let { newName ->
            viewModel.changeSchemaName(careSchemaId, newName)
        }
    }

    private fun deleteSchema() = lifecycleScope.launch {
        val confirmed = appDialog.confirm(
            context = this@EditCareSchemaActivity,
            title = getString(R.string.delete_care_schema)
        )
        if (confirmed) {
            viewModel.deleteSchema(careSchemaId)
            onBackPressed()
        }
    }

    private fun setupStepsRecycler() {
        binding.stepsRecycler.adapter = stepsAdapter
        stepsAdapter.touchHelper.attachToRecyclerView(binding.stepsRecycler)
        stepsAdapter.addSource(viewModel.schemaSteps, this)
    }

    private fun setupNoStepsInfo() {
        viewModel.noSteps.observe(this) { noSteps ->
            binding.noSteps.container.visibleOrGone(noSteps)
            binding.stepsRecycler.visibleOrGone(!noSteps)
        }
    }

    private fun setupAddStepFab() {
        binding.fabAddStep.setOnClickListener {
            addCareSchemaStep()
        }
    }

    private fun addCareSchemaStep() = lifecycleScope.launch {
        appDialog.selectCareStepType(
            context = this@EditCareSchemaActivity
        )?.let { type ->
            viewModel.addSchemaStep(careSchemaId, type)
        }
    }

    private fun saveStepsIfOrderChanged() {
        if (stepsAdapter.stepsOrderChanged) {
            lifecycleScope.launch {
                viewModel.changeSchemaSteps(careSchemaId, stepsAdapter.currentSteps)
            }
        }
    }

    companion object {
        private const val CARE_SCHEMA_ID = "care_schema_id"
    }


}