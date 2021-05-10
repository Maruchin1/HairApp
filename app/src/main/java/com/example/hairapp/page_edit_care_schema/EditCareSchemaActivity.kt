package com.example.hairapp.page_edit_care_schema

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import com.example.core.domain.CareStep
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityEditCareSchemaBinding
import com.example.hairapp.framework.*
import com.example.hairapp.page_care.CareStepsAdapter
import kotlinx.android.synthetic.main.activity_edit_care_schema.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditCareSchemaActivity : AppCompatActivity() {

    val noSteps: LiveData<Boolean>
        get() = liveData {  }

    val editMode: LiveData<Boolean>
        get() = _editMode

    private val viewModel: EditCareSchemaViewModel by viewModel()
    private val dialog: Dialog by inject()
//    private val adapter: CareStepsAdapter = CareStepsAdapter(
//        controller = this,
//        layoutResId = R.layout.item_care_schema_step,
//        dragHandleResId = R.id.item_care_schema_step_drag_handle
//    )
    private val _editMode = MutableLiveData(false)

    fun switchEditMode() {
        _editMode.value = !_editMode.value!!
        Log.d("Activity", "editMode = ${_editMode.value}")
    }

    fun addStep() = lifecycleScope.launch {
        dialog.selectCareStep(this@EditCareSchemaActivity)?.let { type ->
//            adapter.addStep(type, null)
        }
    }

    fun deleteStep(careStep: CareStep) = lifecycleScope.launch {
        val confirmed = dialog.confirm(
            context = this@EditCareSchemaActivity,
            title = getString(R.string.confirm_delete),
            message = getString(R.string.care_schema_confirm_delete_step_message)
        )
        if (confirmed) {
//            adapter.removeStep(careStep.order)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindActivity<ActivityEditCareSchemaBinding>(R.layout.activity_edit_care_schema, viewModel)
        SystemColors(this).allDark()

//        care_schema_steps_recycler.adapter = adapter
//        adapter.touchHelper.attachToRecyclerView(care_schema_steps_recycler)
//        adapter.setSource(viewModel.schemaSteps, this)

        val schemaId = intent.getIntExtra(EXTRA_SCHEMA_ID, -1)
        if (schemaId != -1) {
            selectCareSchema(schemaId)
        }

        toolbar.onMenuOptionClick = {
            when (it.itemId) {
                R.id.option_change_name -> changSchemaName()
                R.id.option_delete -> deleteSchema()
            }
        }
    }

    override fun onBackPressed() {
//        val updatedSteps = adapter.getAllCareSteps()
//        viewModel.saveChanges(updatedSteps)
        super.onBackPressed()
    }

    private fun selectCareSchema(schemaId: Int) = lifecycleScope.launch {
        viewModel.selectCareSchema(schemaId)
            .onFailure { Snackbar.error(this@EditCareSchemaActivity, it) }
    }

    private fun changSchemaName() = lifecycleScope.launch {
        dialog.typeText(
            context = this@EditCareSchemaActivity,
            title = getString(R.string.change_care_schema_name)
        )?.let { newName ->
            viewModel.changeSchemaName(newName)
        }
    }

    private fun deleteSchema() = lifecycleScope.launch {
        val confirmed = dialog.confirm(
            context = this@EditCareSchemaActivity,
            title = getString(R.string.confirm_delete),
            message = getString(R.string.care_schema_confirm_delete_message)
        )
        if (confirmed) {
            viewModel.deleteSchema()
                .onSuccess { finish() }
                .onFailure { Snackbar.error(this@EditCareSchemaActivity, it) }
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