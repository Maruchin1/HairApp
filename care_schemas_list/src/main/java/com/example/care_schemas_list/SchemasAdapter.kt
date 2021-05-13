package com.example.care_schemas_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.care_schemas_list.databinding.ItemSchemaBinding
import com.example.care_schemas_list.databinding.ItemSchemaStepBinding
import com.example.common.base.BaseRecyclerAdapter
import com.example.common.binding.Converter
import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep

internal class SchemasAdapter(
    private val onSchemaClicked: (schema: CareSchema) -> Unit,
    private val context: Context,
    private val stepsAdapter: SchemaStepsAdapter
) : BaseRecyclerAdapter<CareSchema, ItemSchemaBinding>() {

    override fun onBindItemView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemSchemaBinding {
        return ItemSchemaBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindItemData(binding: ItemSchemaBinding, item: CareSchema) {
        binding.apply {
            card.setOnClickListener { onSchemaClicked(item) }
            schemaName.text = makeSchemaName(item)
            item.steps.forEach { addStepToList(binding, it) }
        }
        stepsAdapter.updateItems(item.steps)
    }

    private fun makeSchemaName(schema: CareSchema): String {
        val careText = context.getString(R.string.care)
        return "$careText ${schema.name}"
    }

    private fun addStepToList(binding: ItemSchemaBinding, step: CareSchemaStep) {
        bindStepView(binding.root).let { stepBinding ->
            binding.stepsList.addView(stepBinding.root)
            bindStepData(stepBinding, step)
        }
    }

    private fun bindStepData(binding: ItemSchemaStepBinding, step: CareSchemaStep) {
        binding.apply {
            stepOrdinalNumber.text = (step.order + 1).toString()
            stepName.text = Converter.careStepType(step.type)
        }
    }

    private fun bindStepView(parent: ViewGroup): ItemSchemaStepBinding {
        val inflater = LayoutInflater.from(parent.context)
        return ItemSchemaStepBinding.inflate(inflater)
    }
}