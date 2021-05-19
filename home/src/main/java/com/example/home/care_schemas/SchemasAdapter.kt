package com.example.home.care_schemas

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.common.base.BaseRecyclerAdapter
import com.example.common.binding.Converter
import com.example.common.extensions.visibleOrGone
import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.home.databinding.ItemSchemaBinding
import com.example.home.databinding.ItemSchemaStepBinding

internal class SchemasAdapter(
    private val onSchemaClicked: (schema: CareSchema) -> Unit,
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
            schemaName.text = item.name
            setSteps(binding, item.steps)
            noStepsInSchema.container.visibleOrGone(item.steps.isEmpty())
        }
        stepsAdapter.updateItems(item.steps)
    }

    private fun setSteps(binding: ItemSchemaBinding, steps: List<CareSchemaStep>) {
        binding.stepsList.removeAllViews()
        steps.forEach { addStepToList(binding, it) }
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