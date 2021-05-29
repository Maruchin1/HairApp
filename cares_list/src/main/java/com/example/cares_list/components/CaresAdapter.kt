package com.example.cares_list.components

import com.example.cares_list.databinding.ItemCareBinding
import com.example.corev2.entities.Care
import com.example.corev2.entities.PehBalance
import com.example.corev2.relations.CareWithStepsAndPhotos
import com.example.corev2.service.formatDayOfMonth
import com.example.corev2.service.formatShortMonth
import com.example.corev2.ui.BaseRecyclerAdapter

internal class CaresAdapter : BaseRecyclerAdapter<CareWithStepsAndPhotos, ItemCareBinding>(
    bindingInflater = ItemCareBinding::inflate
) {
    override fun onSetItemData(binding: ItemCareBinding, item: CareWithStepsAndPhotos) {
        binding.apply {
            dayOfMonth.text = item.care.date.formatDayOfMonth()
            month.text = item.care.date.formatShortMonth()
            schemaName.text = item.care.schemaName

            val products = item.steps.mapNotNull { it.product }
            pehBalance.pehBalance = PehBalance.fromProducts(products)
        }
    }
}