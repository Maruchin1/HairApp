package com.example.hairapp

import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

@BindingAdapter("app:items")
fun setChipGroupItems(view: ChipGroup, items: Collection<String>?) {
    items?.forEach {
        val chip = Chip(view.context)
        chip.text = it
        chip.isClickable = true
        chip.isCheckable = true
        view.addView(chip)
    }
}