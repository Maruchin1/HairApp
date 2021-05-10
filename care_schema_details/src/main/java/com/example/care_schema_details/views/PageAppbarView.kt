package com.example.care_schema_details.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.care_schema_details.R
import com.example.care_schema_details.databinding.ViewCareSchemaDetailsAppbarBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar

class PageAppbarView(context: Context, attrs: AttributeSet) : AppBarLayout(context, attrs),
    Toolbar.OnMenuItemClickListener {

    var title: String
        get() = binding.toolbar.title.text.toString()
        set(value) {
            binding.toolbar.title.text = value
        }

    private val binding = ViewCareSchemaDetailsAppbarBinding
        .inflate(LayoutInflater.from(context), this)

    private var onChangeSchemaName: (() -> Unit)? = null
    private var onDeleteSchema: (() -> Unit)? = null

    init {
        binding.toolbar.toolbar.let {
            it.inflateMenu(R.menu.care_schema_details_toolbar)
            it.setOnMenuItemClickListener(this::onMenuItemClick)
        }
    }

    fun setOnChangeSchemaName(callback: () -> Unit) {
        onChangeSchemaName = callback
    }

    fun setOnDeleteSchema(callback: () -> Unit) {
        onDeleteSchema = callback
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.option_change_schema_name -> onChangeSchemaName?.invoke()
            R.id.option_delete_schema -> onDeleteSchema?.invoke()
        }
        return true
    }
}