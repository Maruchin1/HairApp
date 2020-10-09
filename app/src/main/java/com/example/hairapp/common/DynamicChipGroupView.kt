package com.example.hairapp.common

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.example.hairapp.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.view_dynamic_chip_group.view.*

class DynamicChipGroupView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val _selectedItems = MutableLiveData<List<String>>(listOf())

    var itemsSelectable: Boolean = false

    var items: List<String> = listOf()
        set(value) {
            field = value
            updateDisplayedChips()
            updateSelectedChips()
        }

    var selectedItems: List<String>
        get() = _selectedItems.value!!
        set(value) {
            _selectedItems.value = value
            updateSelectedChips()
        }

    init {
        inflate(context, R.layout.view_dynamic_chip_group, this)
        context.obtainStyledAttributes(attrs, R.styleable.DynamicChipGroupView).run {
            itemsSelectable = getBoolean(R.styleable.DynamicChipGroupView_itemsSelectable, false)
            recycle()
        }
    }

    private fun updateDisplayedChips() {
        chip_group.removeAllViews()
        items.forEach { text ->
            val chip = Chip(context)
            if (!itemsSelectable) {
                chip.chipBackgroundColor = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.color_primary)
                )
                chip.setTextColor(ContextCompat.getColor(context, R.color.color_white))
            }
            chip.id = View.generateViewId()
            chip.text = text
            chip.isClickable = true
            chip.isCheckable = true
            chip_group.addView(chip)
            if (itemsSelectable) {
                registerCheckedListener(chip)
            }
        }
    }

    private fun updateSelectedChips() {
        val selectedItems = this.selectedItems
        val chips = chip_group.children as Sequence<Chip>
        chips.forEach { chip ->
            val chipText = chip.text.toString()
            chip.isChecked = selectedItems.contains(chipText)
        }
    }


    private fun registerCheckedListener(chip: Chip) {
        chip.setOnCheckedChangeListener { _, _ ->
            val chips = chip_group.children as Sequence<Chip>
            val selectedChips = chips.filter { it.isChecked }
            _selectedItems.value = selectedChips.map { it.text.toString() }.toList()
        }
    }

    companion object {

        @BindingAdapter("app:items")
        @JvmStatic
        fun setItems(view: DynamicChipGroupView, items: List<String>?) {
            items?.let { view.items = it }
        }

        @BindingAdapter("app:selectedItems")
        @JvmStatic
        fun setSelectedItems(view: DynamicChipGroupView, selectedItems: List<String>?) {
            selectedItems?.let { view.selectedItems = it }
        }

        @InverseBindingAdapter(attribute = "app:selectedItems")
        @JvmStatic
        fun getSelectedItems(view: DynamicChipGroupView): List<String> {
            return view._selectedItems.value!!
        }

        @BindingAdapter("app:selectedItemsAttrChanged")
        @JvmStatic
        fun setSelectedItemsChangedListener(
            view: DynamicChipGroupView,
            attrChange: InverseBindingListener
        ) {
            view._selectedItems.observeForever { attrChange.onChange() }
        }
    }
}