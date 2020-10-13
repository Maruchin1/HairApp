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

    fun addStep() = selectCareStepDialog {
        adapter.addStep(it, null)
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
        bind<ActivityEditCareSchemaBinding>(R.layout.activity_edit_care_schema, null)
        setStatusBarColor(R.color.color_primary)
        setNavigationColor(R.color.color_background)

        care_schema_steps_recycler.adapter = adapter
        adapter.touchHelper.attachToRecyclerView(care_schema_steps_recycler)
//        adapter.setSource(viewModel.careSchema, this)
    }

    override fun onBackPressed() {
        checkSchemaChanged()
    }

    private fun checkSchemaChanged() = lifecycleScope.launch {
        val newSchema = adapter.getAllCareSteps()
//        if (viewModel.schemaChanged(newSchema) && confirmSaveChanges()) {
//            saveChanges(newSchema)
//        } else {
//            super.onBackPressed()
//        }
    }

    private suspend fun confirmSaveChanges() = confirmDialog(
        title = "Zapisz zmiany",
        message = "Schemat pielęgnacji został zmodyfikowany. Chcesz zapisać wprowadzone zmiany?"
    )

//    private suspend fun saveChanges(newSchema: List<CareStep>) {
//        viewModel.saveChanges(newSchema)
//            .onSuccess { super.onBackPressed() }
//            .onFailure { showErrorSnackbar(it.message) }
//    }

    companion object {
        fun makeIntent(context: Context): Intent {
            return Intent(context, EditCareSchemaActivity::class.java)
        }
    }
}