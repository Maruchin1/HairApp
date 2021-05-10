package com.example.care_schema_details

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.care_schema_details.components.CareSchemaDetailsViewModel
import com.example.care_schema_details.components.CareSchemaStepsAdapter
import com.example.care_schema_details.components.FabAdapter
import com.example.care_schema_details.components.ToolbarMenuListener
import com.example.care_schema_details.databinding.ActivityCareSchemaDetailsBinding
import com.example.common.base.BaseFeatureActivity
import com.example.common.modals.AppDialog
import com.example.common.view_handlers.AppToolbarHandler
import com.example.core.domain.CareSchemaStep
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CareSchemaDetailsActivity : BaseFeatureActivity<ActivityCareSchemaDetailsBinding>(
    featureModule = careSchemaDetailsModule,
    layoutId = R.layout.activity_care_schema_details
), AppToolbarHandler {

    private val careSchemaId: Int
        get() = intent.getIntExtra(CARE_SCHEMA_ID, -1)

    private val viewModel: CareSchemaDetailsViewModel by viewModel { parametersOf(careSchemaId) }
    private val appDialog: AppDialog by inject()
    private val stepsAdapter: CareSchemaStepsAdapter by inject { parametersOf(this, viewModel) }
    private val fabAdapter: FabAdapter by inject { parametersOf(this, viewModel) }

    fun deleteStep(step: CareSchemaStep) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.controller = this
        binding.viewModel = viewModel
        setupToolbarMenu()
        setupStepsRecycler()
        setupFabAdapter()
    }

    override fun onNavigationClick() {
        onBackPressed()
    }

    private fun setupToolbarMenu() {
        binding.appbar.toolbar.toolbar.run {
            inflateMenu(R.menu.care_schema_details_toolbar)
            setOnMenuItemClickListener(
                ToolbarMenuListener(
                    context = this@CareSchemaDetailsActivity,
                    coroutineScope = lifecycleScope,
                    viewModel = viewModel,
                    appDialog = appDialog
                )
            )
        }
    }

    private fun setupStepsRecycler() {
        binding.stepsRecycler.adapter = stepsAdapter
        stepsAdapter.touchHelper.attachToRecyclerView(binding.stepsRecycler)
        stepsAdapter.addSource(viewModel.schemaSteps, this)
    }

    private fun setupFabAdapter() {
        fabAdapter
    }

    companion object {
        private const val CARE_SCHEMA_ID = "care_schema_id"
    }
}