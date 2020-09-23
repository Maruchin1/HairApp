package com.example.hairapp.framework

import android.content.res.ColorStateList
import android.net.Uri
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import coil.load
import com.example.core.domain.Care
import com.example.hairapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.time.LocalDate

object Binder {

    // ChipGroup

    @BindingAdapter("app:items", "app:itemsSelectable", requireAll = false)
    @JvmStatic
    fun setChipGroupItems(view: ChipGroup, items: Collection<String>?, itemsSelectable: Boolean?) {
        view.removeAllViews()
        items?.forEach {
            val chip = Chip(view.context)
            if (itemsSelectable == false) {
                chip.chipBackgroundColor =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            view.context,
                            R.color.color_primary
                        )
                    )
                chip.setTextColor(ContextCompat.getColor(view.context, R.color.color_white))
            }
            chip.id = View.generateViewId()
            chip.text = it
            chip.isClickable = true
            chip.isCheckable = true
            view.addView(chip)
        }
    }

    @BindingAdapter("app:selectedItems")
    @JvmStatic
    fun setSelectedItems(view: ChipGroup, items: Set<String>?) {
        items ?: return
        val chips = view.children as Sequence<Chip>
        chips.forEach { chip ->
            val chipValue = chip.text.toString()
            chip.isChecked = items.contains(chipValue)
        }
    }

    @InverseBindingAdapter(attribute = "app:selectedItems")
    @JvmStatic
    fun getSelectedItems(view: ChipGroup): Set<String> {
        val chips = view.children as Sequence<Chip>
        val selectedChips = chips.filter { it.isChecked }
        return selectedChips.map { it.text.toString() }.toSet()
    }

    @BindingAdapter("app:selectedItemsAttrChanged")
    @JvmStatic
    fun setSelectedItemsChangedListener(view: ChipGroup, attrChange: InverseBindingListener) {
        val chips = view.children as Sequence<Chip>
        chips.forEach { chip ->
            chip.setOnCheckedChangeListener { _, _ ->
                attrChange.onChange()
            }
        }
    }

    // Photo

    @BindingAdapter("app:srcUri", "app:defaultResource", requireAll = false)
    @JvmStatic
    fun setSourceUri(view: ImageView, uri: Uri?, defaultResourceId: Int?) {
        when {
            uri != null -> view.load(uri)
            defaultResourceId != null -> view.load(defaultResourceId)
            else -> view.load(R.drawable.ic_round_photo_24)
        }
    }

    @BindingAdapter("app:srcUri", "app:defaultResource", requireAll = false)
    @JvmStatic
    fun setSourceUri(view: ImageView, data: String?, defaultResourceId: Int?) {
        val uri = data?.let { Uri.parse(it) }
        setSourceUri(view, uri, defaultResourceId)
    }

    // Toolbar

    @BindingAdapter("app:onNavigationClick")
    @JvmStatic
    fun setOnNavigationClick(view: MaterialToolbar, action: (() -> Unit)?) {
        view.setNavigationOnClickListener { action?.invoke() }
    }

    // Visibility

    @BindingAdapter("app:visibleOrGone")
    @JvmStatic
    fun setVisibleOrGone(view: View, visible: Boolean?) {
        view.visibility = if (visible == true) View.VISIBLE else View.GONE
    }

    // EditText

    @BindingAdapter("app:careType")
    @JvmStatic
    fun setCareType(view: EditText, type: Care.Type?) {
        val currTypeText = view.text.toString()
        val newTypeText = Converter.careType(type)
        if (currTypeText != newTypeText) {
            view.setText(newTypeText)
        }
    }

    @BindingAdapter("app:careTypeOptions")
    @JvmStatic
    fun setCareTypeOptions(view: AutoCompleteTextView, options: Array<Care.Type>) {
        val stringOptions = options.map { Converter.careType(it) }
        val adapter = ArrayAdapter(view.context, R.layout.item_menu, stringOptions)
        view.setAdapter(adapter)
    }

    @BindingAdapter("app:date")
    @JvmStatic
    fun setDate(view: EditText, date: LocalDate?) {
        view.setText(Converter.date(date))
    }

    @InverseBindingAdapter(attribute = "app:date")
    @JvmStatic
    fun getDate(view: EditText): LocalDate? {
        return Converter.inverseDate(view.text.toString())
    }
}
