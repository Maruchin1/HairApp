package com.example.edit_care_schema

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common.base.BaseActivity
import com.example.common.base.SystemColors
import com.example.common.extensions.visibleOrGone
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.ui.DialogService
import com.example.edit_care_schema.databinding.ActivityEditCareSchemaBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal class EditCareSchemaActivity : BaseActivity<ActivityEditCareSchemaBinding>(),
    CareSchemaStepsAdapter.Handler {

    val careSchemaId: Long
        get() = intent.getLongExtra(CARE_SCHEMA_ID, -1)

    private val viewModel: EditCareSchemaViewModel by viewModels()

    @Inject
    internal lateinit var dialogService: DialogService

    @Inject
    internal lateinit var stepsAdapter: CareSchemaStepsAdapter


    override fun bindActivity(): ActivityEditCareSchemaBinding {
        return ActivityEditCareSchemaBinding.inflate(layoutInflater)
    }

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
        val x = 1
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.apply {
            lightStatusBar()
            lightNavigationBar()
        }
    }

    override fun onStepLongClick(step: CareSchemaStep) {
        lifecycleScope.launch {
            dialogService.confirm(
                context = this@EditCareSchemaActivity,
                title = "Usunąć krok ${step.order + 1} ${getString(step.prouctType.resId)}?"
            ).let { confirmed ->
                if (confirmed) {
                    viewModel.deleteStep(step)
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
        dialogService.typeText(
            context = this@EditCareSchemaActivity,
            title = getString(R.string.change_care_schema_name),
            currentValue = viewModel.schemaName.value
        )?.let { newName ->
            viewModel.changeSchemaName(newName)
        }
    }

    private fun deleteSchema() = lifecycleScope.launch {
        dialogService.confirm(
            context = this@EditCareSchemaActivity,
            title = getString(R.string.delete_care_schema)
        ).let { confirmed ->
            if (confirmed) {
                viewModel.deleteSchema()
                finish()
            }
        }
    }

    private fun setupStepsRecycler() {
        stepsAdapter.handler = this
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
        dialogService.selectProductType(
            context = this@EditCareSchemaActivity
        )?.let { type ->
            viewModel.addStep(type)
        }
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