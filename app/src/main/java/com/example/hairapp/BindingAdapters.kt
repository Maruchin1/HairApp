package com.example.hairapp

import android.view.View
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

@BindingAdapter("app:items")
fun setChipGroupItems(view: ChipGroup, items: Collection<String>?) {
    items?.forEach {
        val chip = Chip(view.context)
        chip.id = View.generateViewId()
        chip.text = it
        chip.isClickable = true
        chip.isCheckable = true
        view.addView(chip)
    }
}

@BindingAdapter("app:selectedItems")
fun setSelectedItems(view: ChipGroup, items: Set<String>?) {
    items ?: return
    val chips = view.children as Sequence<Chip>
    chips.forEach { chip ->
        val chipValue = chip.text.toString()
        chip.isChecked = items.contains(chipValue)
    }
}

@InverseBindingAdapter(attribute = "app:selectedItems")
fun getSelectedItems(view: ChipGroup): Set<String> {
    val chips = view.children as Sequence<Chip>
    val selectedChips = chips.filter { it.isChecked }
    return selectedChips.map { it.text.toString() }.toSet()
}

@BindingAdapter("app:selectedItemsAttrChanged")
fun setSelectedItemsChangedListener(view: ChipGroup, attrChange: InverseBindingListener) {
    val chips = view.children as Sequence<Chip>
    chips.forEach { chip ->
        chip.setOnCheckedChangeListener { _, _ ->
            attrChange.onChange()
        }
    }
}
