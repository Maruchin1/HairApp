package com.example.care_schemas_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.care_schemas_list.databinding.ItemSchemaStepBinding
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.ui.BaseRecyclerAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class SchemaStepsAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseRecyclerAdapter<CareSchemaStep, ItemSchemaStepBinding>() {

    override fun onBindItemView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemSchemaStepBinding {
        return ItemSchemaStepBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindItemData(binding: ItemSchemaStepBinding, item: CareSchemaStep) {
        binding.apply {
            stepOrdinalNumber.text = (item.order + 1).toString()
            stepName.text = context.getString(item.prouctType.resId)
        }
    }
}