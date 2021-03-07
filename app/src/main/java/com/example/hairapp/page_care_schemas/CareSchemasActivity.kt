package com.example.hairapp.page_care_schemas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.core.domain.CareSchema
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityCareSchemasBinding
import com.example.hairapp.framework.*
import com.example.hairapp.page_edit_care_schema.EditCareSchemaActivity
import kotlinx.android.synthetic.main.activity_care_schemas.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareSchemasActivity : AppCompatActivity() {

    private val viewModel: CareSchemasViewModel by viewModel()

    private val adapter by lazy {
        BindingRecyclerAdapter<CareSchema>(this, R.layout.item_care_schema)
    }

    fun addNewSchema() = lifecycleScope.launch {
        Dialog.typeText(
            context = this@CareSchemasActivity,
            title = getString(R.string.name_your_schema)
        )?.let { name ->
            viewModel.addCareSchema(name)
                .onSuccess { openSchemaPage(it) }
                .onFailure { Snackbar.error(this@CareSchemasActivity, it) }
        }
    }

    fun openSchemaPage(schemaId: Int) {
        val intent = EditCareSchemaActivity.makeIntent(this, schemaId)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityCareSchemasBinding>(R.layout.activity_care_schemas, viewModel)
        setSystemColors(R.color.color_primary)

        care_schemas_recycler.adapter = adapter
        adapter.setSource(viewModel.careSchemas, this)
    }

    companion object {
        fun makeIntent(context: Context): Intent {
            return Intent(context, CareSchemasActivity::class.java)
        }
    }
}