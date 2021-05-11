package com.example.care_schema_details.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.care_schema_details.R
import com.example.care_schema_details.databinding.ViewCareSchemaDetailsAppbarBinding
import com.example.common.databinding.ViewAppToolbarBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar

class PageAppbarView(context: Context, attrs: AttributeSet) : AppBarLayout(context, attrs) {

    var title: String
        get() = binding.title.text.toString()
        set(value) {
            binding.title.text = value
        }

    private val binding = ViewAppToolbarBinding
        .inflate(LayoutInflater.from(context), this, true)

    private var onNavigationClick: (() -> Unit)? = null

    init {
        binding.toolbar.inflateMenu(R.menu.care_schema_details_toolbar)
    }

    fun setOnNavigationClick(callback: () -> Unit) {
        onNavigationClick = callback
    }
}