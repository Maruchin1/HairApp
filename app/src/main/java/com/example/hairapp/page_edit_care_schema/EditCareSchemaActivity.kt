package com.example.hairapp.page_edit_care_schema

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.core.domain.CareStep
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityEditCareSchemaBinding
import com.example.hairapp.framework.*
import com.example.hairapp.page_care.CareStepsAdapter
import kotlinx.android.synthetic.main.activity_edit_care_schema.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditCareSchemaActivity : AppCompatActivity() {

    val noSteps: LiveData<Boolean>
        get() = adapter.noSteps

    private val viewModel: EditCareSchemaViewModel by viewModel()

    @SuppressLint("ClickableViewAccessibility")
    private val adapter: CareStepsAdapter = CareStepsAdapter(
        controller = this,
        layoutResId = R.layout.item_care_schema_step,
        dragHandleResId = R.id.item_care_schema_step_drag_handle
    )

    fun addStep() = lifecycleScope.launch {
        selectCareStepDialog()?.let { type ->
            adapter.addStep(type, null)
        }
    }

    fun deleteStep(careStep: CareStep) = lifecycleScope.launch {
        val confirmed = confirmDialog(
            title = getString(R.string.confirm_delete),
            message = getString(R.string.care_schema_confirm_delete_step_message)
        )
        if (confirmed) {
            adapter.removeStep(careStep.order)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityEditCareSchemaBinding>(R.layout.activity_edit_care_schema, viewModel)
        setSystemColors(R.color.color_primary)

        care_schema_steps_recycler.adapter = adapter
        adapter.touchHelper.attachToRecyclerView(care_schema_steps_recycler)
        adapter.setSource(viewModel.schemaSteps, this)

        val schemaId = intent.getIntExtra(EXTRA_SCHEMA_ID, -1)
        if (schemaId != -1) {
            selectCareSchema(schemaId)
        }

        toolbar.onMenuOptionClick = {
            when (it.itemId) {
                R.id.option_change_name -> askForNewName()
                R.id.option_delete -> deleteSchema()
            }
        }
    }

    override fun onBackPressed() {
        val updatedSteps = adapter.getAllCareSteps()
        viewModel.saveChanges(updatedSteps)
        super.onBackPressed()
    }

    private fun selectCareSchema(schemaId: Int) = lifecycleScope.launch {
        viewModel.selectCareSchema(schemaId)
            .onFailure { showErrorSnackbar(it.message) }
    }

    private fun askForNewName() = lifecycleScope.launch {
        inputDialog(getString(R.string.name_your_schema))?.let { newName ->
            viewModel.changeSchemaName(newName)
        }
    }

    private fun deleteSchema() = lifecycleScope.launch {
        val confirmed = confirmDialog(
            title = getString(R.string.confirm_delete),
            message = getString(R.string.care_schema_confirm_delete_message)
        )
        if (confirmed) {
            viewModel.deleteSchema()
                .onSuccess { finish() }
                .onFailure { showErrorSnackbar(it.message) }
        }
    }

    companion object {
        private const val EXTRA_SCHEMA_ID = "extra-schema-id"

        fun makeIntent(context: Context, schemaId: Int): Intent {
            return Intent(context, EditCareSchemaActivity::class.java)
                .putExtra(EXTRA_SCHEMA_ID, schemaId)
        }
    }
}