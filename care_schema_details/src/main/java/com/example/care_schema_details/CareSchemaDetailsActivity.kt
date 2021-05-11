package com.example.care_schema_details

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.care_schema_details.components.CareSchemaActions
import com.example.care_schema_details.components.CareSchemaDetailsViewModel
import com.example.care_schema_details.components.CareSchemaStepsAdapter
import com.example.care_schema_details.databinding.ActivityCareSchemaDetailsBinding
import com.example.care_schema_details.use_case.AddSchemaStepUseCase
import com.example.common.base.BaseFeatureActivity
import com.example.common.modals.ActionsModal
import com.example.common.modals.AppDialog
import com.example.common.modals.AppModal
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CareSchemaDetailsActivity : BaseFeatureActivity<ActivityCareSchemaDetailsBinding>(
    featureModule = careSchemaDetailsModule,
) {

    private val careSchemaId: Int
        get() = intent.getIntExtra(CARE_SCHEMA_ID, -1)

    private val viewModel: CareSchemaDetailsViewModel by viewModel { parametersOf(careSchemaId) }
    private val appDialog: AppDialog by inject()
    private val appModal: AppModal by inject()
    private val addSchemaStep: AddSchemaStepUseCase by inject()
    private val stepsAdapter: CareSchemaStepsAdapter by inject { parametersOf(this, viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAppbar()
        setupStepsRecycler()
        setupAddStepFab()
    }

    override fun bindActivity(): ActivityCareSchemaDetailsBinding {
        return ActivityCareSchemaDetailsBinding.inflate(layoutInflater)
    }

    private fun setupAppbar() {
        binding.appbar.setOnNavigationClick { onBackPressed() }
        viewModel.schemaName.observe(this) {
            binding.appbar.title = it
        }
    }


    private fun setupStepsRecycler() {
        binding.stepsRecycler.adapter = stepsAdapter
        stepsAdapter.touchHelper.attachToRecyclerView(binding.stepsRecycler)
        stepsAdapter.addSource(viewModel.schemaSteps, this)
        stepsAdapter.setItemComparator { oldItem, newItem -> oldItem == newItem }
    }

    private fun setupAddStepFab() {
        binding.fabEditSchema.setOnClickListener {
            openActionsModal()
        }
    }

    private fun openActionsModal() = lifecycleScope.launch {
        appModal.openActions(
            manager = supportFragmentManager,
            actions = CareSchemaActions.values().toList()
        )?.let { action -> onSchemaAction(action) }
    }

    private fun onSchemaAction(action: CareSchemaActions) {
        when (action) {
            CareSchemaActions.CHANGE_SCHEMA_NAME -> changeSchemaName()
            CareSchemaActions.CHANGE_STEPS_ORDER -> changeStepsOrder()
            CareSchemaActions.ADD_STEP -> addCareSchemaStep()
            CareSchemaActions.DELETE_SCHEMA -> deleteSchema()
        }
    }

    private fun changeSchemaName() = lifecycleScope.launch {
        appDialog.typeText(
            context = this@CareSchemaDetailsActivity,
            title = getString(R.string.change_care_schema_name),
            currentValue = viewModel.getSchemaName()
        )?.let { newName ->
            viewModel.changeSchemaName(newName)
        }
    }

    private fun changeStepsOrder() {
        viewModel.enableStepsEditMode()
    }

    private fun addCareSchemaStep() = lifecycleScope.launch {
        appDialog.selectCareStepType(
            context = this@CareSchemaDetailsActivity
        )?.let { type ->
            addSchemaStep(careSchemaId, type)
        }
    }

    private fun deleteSchema() = lifecycleScope.launch {
        val confirmed = appDialog.confirm(
            context = this@CareSchemaDetailsActivity,
            title = getString(R.string.delete_care_schema)
        )
        if (confirmed) {
            viewModel.deleteSchema()
        }
    }

    companion object {
        private const val CARE_SCHEMA_ID = "care_schema_id"
    }


}