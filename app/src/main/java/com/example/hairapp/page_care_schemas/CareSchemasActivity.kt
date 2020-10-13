package com.example.hairapp.page_care_schemas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.core.domain.CareSchema
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityCareSchemasBinding
import com.example.hairapp.framework.BindingRecyclerAdapter
import com.example.hairapp.framework.bind
import kotlinx.android.synthetic.main.activity_care_schemas.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareSchemasActivity : AppCompatActivity() {

    private val viewModel: CareSchemasViewModel by viewModel()

    private val adapter by lazy {
        BindingRecyclerAdapter<CareSchema>(this, R.layout.item_care_schema)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityCareSchemasBinding>(R.layout.activity_care_schemas, viewModel)

        care_schemas_recycler.adapter = adapter
        adapter.setSource(viewModel.careSchemas, this)
    }

    companion object {
        fun makeIntent(context: Context): Intent {
            return Intent(context, CareSchemasActivity::class.java)
        }
    }
}