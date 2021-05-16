package com.example.edit_care_schema

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.edit_care_schema.components.EditCareSchemaViewModel
import com.example.edit_care_schema.components.CareSchemaStepsAdapter
import com.example.edit_care_schema.use_case.AddSchemaStepUseCase
import com.example.edit_care_schema.use_case.ChangeSchemaNameUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaUseCase
import com.example.common.base.BaseFeatureActivity
import com.example.common.base.SystemColors
import com.example.common.extensions.visibleOrGone
import com.example.common.modals.AppDialog
import com.example.edit_care_schema.databinding.ActivityCareSchemaDetailsBinding
import com.example.edit_care_schema.use_case.ChangeSchemaStepsUseCase
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditCareSchemaActivity : BaseFeatureActivity<ActivityCareSchemaDetailsBinding>(
    featureModule = careSchemaDetailsModule,
) {

    private val careSchemaId: Int
        get() = intent.getIntExtra(CARE_SCHEMA_ID, -1)

    private val viewModel: EditCareSchemaViewModel by viewModel { parametersOf(careSchemaId) }
    private val appDialog: AppDialog by inject()
    private val addSchemaStep: AddSchemaStepUseCase by inject()
    private val stepsAdapter: CareSchemaStepsAdapter by inject { parametersOf(this, viewModel) }
    private val changeSchemaNameUseCase: ChangeSchemaNameUseCase by inject()
    private val changeSchemaStepsUseCase: ChangeSchemaStepsUseCase by inject()
    private val deleteSchemaUseCase: DeleteSchemaUseCase by inject()

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
            changeSchemaNameUseCase(careSchemaId, newName)
        }
    }

    private fun deleteSchema() = lifecycleScope.launch {
        val confirmed = appDialog.confirm(
            context = this@EditCareSchemaActivity,
            title = getString(R.string.delete_care_schema)
        )
        if (confirmed) {
            deleteSchemaUseCase(careSchemaId)
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
            addSchemaStep(careSchemaId, type)
        }
    }

    private fun saveStepsIfOrderChanged() {
        if (stepsAdapter.stepsOrderChanged) {
            lifecycleScope.launch {
                changeSchemaStepsUseCase(careSchemaId, stepsAdapter.currentSteps)
            }
        }
    }

    companion object {
        private const val CARE_SCHEMA_ID = "care_schema_id"
    }


}