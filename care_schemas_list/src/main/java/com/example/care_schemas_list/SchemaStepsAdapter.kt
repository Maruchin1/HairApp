package com.example.care_schemas_list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.care_schemas_list.databinding.ItemSchemaStepBinding
import com.example.common.base.BaseRecyclerAdapter
import com.example.common.binding.Converter
import com.example.core.domain.CareSchemaStep

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