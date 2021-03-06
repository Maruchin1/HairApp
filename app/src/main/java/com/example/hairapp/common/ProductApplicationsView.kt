package com.example.hairapp.common

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.example.core.domain.Product
import com.example.hairapp.R
import com.example.hairapp.framework.Converter
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.view_product_applications.view.*

class ProductApplicationsView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val _selectedItems = MutableLiveData<List<Product.Application>>(listOf())

    var itemsSelectable: Boolean = false

    var items: List<Product.Application> = listOf()
        set(value) {
            field = value
            updateDisplayedChips()
            updateSelectedChips()
        }

    var selectedItems: List<Product.Application>
        get() = _selectedItems.value!!
        set(value) {
            _selectedItems.value = value
            updateSelectedChips()
        }

    init {
        inflate(context, R.layout.view_product_applications, this)
        context.obtainStyledAttributes(attrs, R.styleable.ProductApplicationsView).run {
            itemsSelectable = getBoolean(R.styleable.ProductApplicationsView_itemsSelectable, false)
            recycle()
        }
    }

    private fun updateDisplayedChips() {
        chip_group.removeAllViews()
        items.forEach { productApplication ->
            val chip = Chip(context)
            if (!itemsSelectable) {
                chip.chipBackgroundColor = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.color_primary_dark)
                )
                chip.setTextColor(ContextCompat.getColor(context, R.color.color_white))
            }
            chip.id = View.generateViewId()
            chip.text = Converter.productApplication(productApplication)
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
            val chipItemValue = Converter.inverseProductApplication(chipText)
            chip.isChecked = selectedItems.contains(chipItemValue)
        }
    }


    private fun registerCheckedListener(chip: Chip) {
        chip.setOnCheckedChangeListener { _, _ ->
            val chips = chip_group.children as Sequence<Chip>
            val selectedChips = chips.filter { it.isChecked }
            _selectedItems.value = selectedChips
                .toList()
                .mapNotNull { Converter.inverseProductApplication(it.text.toString()) }
                .also { Log.d("ProductApplicationsView", it.toString()) }
        }
    }

    companion object {

        @BindingAdapter("app:items")
        @JvmStatic
        fun setItems(view: ProductApplicationsView, items: List<Product.Application>?) {
            items?.let { view.items = it }
        }

        @BindingAdapter("app:selectedItems")
        @JvmStatic
        fun setSelectedItems(view: ProductApplicationsView, selectedItems: List<Product.Application>?) {
            selectedItems?.let { view.selectedItems = it }
        }

        @InverseBindingAdapter(attribute = "app:selectedItems")
        @JvmStatic
        fun getSelectedItems(view: ProductApplicationsView): List<Product.Application> {
            return view._selectedItems.value!!
        }

        @BindingAdapter("app:selectedItemsAttrChanged")
        @JvmStatic
        fun setSelectedItemsChangedListener(
            view: ProductApplicationsView,
            attrChange: InverseBindingListener
        ) {
            view._selectedItems.observeForever { attrChange.onChange() }
        }
    }
}