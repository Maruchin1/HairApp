package com.example.cares_list.components

import com.example.cares_list.databinding.ItemCareBinding
import com.example.corev2.entities.Care
import com.example.corev2.service.formatDayOfMonth
import com.example.corev2.service.formatShortMonth
import com.example.corev2.ui.BaseRecyclerAdapter

internal class CaresAdapter : BaseRecyclerAdapter<Care, ItemCareBinding>(
    bindingInflater = ItemCareBinding::inflate
) {
    override fun onSetItemData(binding: ItemCareBinding, item: Care) {
        binding.apply {
            dayOfMonth.text = item.date.formatDayOfMonth()
            month.text = item.date.formatShortMonth()
            schemaName.text = item.schemaName
        }
    }
}