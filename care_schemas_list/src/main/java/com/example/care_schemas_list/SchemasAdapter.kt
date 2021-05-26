package com.example.care_schemas_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.care_schemas_list.databinding.ItemSchemaBinding
import com.example.care_schemas_list.databinding.ItemSchemaStepBinding
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.corev2.ui.BaseRecyclerAdapter
import com.example.corev2.ui.setVisibleOrGone

internal class SchemasAdapter(
    private val context: Context,
    private val stepsAdapter: SchemaStepsAdapter
) : BaseRecyclerAdapter<CareSchemaWithSteps, ItemSchemaBinding>() {

    var handler: Handler? = null

    override fun onBindItemView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemSchemaBinding {
        return ItemSchemaBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindItemData(binding: ItemSchemaBinding, item: CareSchemaWithSteps) {
        binding.apply {
            card.setOnClickListener { handler?.onCareSchemaClicked(item.careSchema) }
            schemaName.text = item.careSchema.name
            setSteps(binding, item.steps)
            noStepsInSchema.container.setVisibleOrGone(item.steps.isEmpty())
        }
        stepsAdapter.updateItems(item.steps)
    }

    private fun setSteps(binding: ItemSchemaBinding, steps: List<CareSchemaStep>) {
        binding.stepsList.removeAllViews()
        steps
            .sortedBy { it.order }
            .forEach { addStepToList(binding, it) }
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
            stepName.text = context.getString(step.prouctType.resId)
        }
    }

    private fun bindStepView(parent: ViewGroup): ItemSchemaStepBinding {
        val inflater = LayoutInflater.from(parent.context)
        return ItemSchemaStepBinding.inflate(inflater)
    }

    interface Handler {
        fun onCareSchemaClicked(careSchema: CareSchema)
    }
}