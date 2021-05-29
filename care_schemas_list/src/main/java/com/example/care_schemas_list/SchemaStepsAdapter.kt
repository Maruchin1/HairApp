package com.example.care_schemas_list

import android.content.Context
import com.example.care_schemas_list.databinding.ItemSchemaStepBinding
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.ui.BaseRecyclerAdapter
import com.example.corev2.ui.InflateBinding

internal class SchemaStepsAdapter(
    private val context: Context
) : BaseRecyclerAdapter<CareSchemaStep, ItemSchemaStepBinding>(
    bindingInflater = ItemSchemaStepBinding::inflate
) {

    override fun onSetItemData(binding: ItemSchemaStepBinding, item: CareSchemaStep) {
        binding.apply {
            stepOrdinalNumber.text = (item.order + 1).toString()
            stepName.text = context.getString(item.productType.resId)
        }
    }
}