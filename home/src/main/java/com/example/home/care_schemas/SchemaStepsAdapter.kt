package com.example.home.care_schemas

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.common.base.BaseRecyclerAdapter
import com.example.common.binding.Converter
import com.example.core.domain.CareSchemaStep
import com.example.home.databinding.ItemSchemaStepBinding

class SchemaStepsAdapter: BaseRecyclerAdapter<CareSchemaStep, ItemSchemaStepBinding>() {

    override fun onBindItemView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemSchemaStepBinding {
        return ItemSchemaStepBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindItemData(binding: ItemSchemaStepBinding, item: CareSchemaStep) {
        binding.apply {
            stepOrdinalNumber.text = (item.order + 1).toString()
            stepName.text = Converter.careStepType(item.type)
        }
    }
}