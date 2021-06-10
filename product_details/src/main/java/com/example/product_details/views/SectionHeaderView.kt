package com.example.product_details.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.example.product_details.R
import com.example.product_details.databinding.ViewSectionHeaderBinding
import com.example.shared_ui.layoutInflater

internal class SectionHeaderView(
    context: Context,
    attrs: AttributeSet
) : FrameLayout(context, attrs) {

    private val binding = ViewSectionHeaderBinding.inflate(layoutInflater, this, true)

    var sectionIcon: Drawable?
        get() = binding.labelIcon.drawable
        set(value) {
            binding.labelIcon.setImageDrawable(value)
        }

    var sectionTitle: String
        get() = binding.label.text.toString()
        set(value) {
            binding.label.text = value
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.SectionHeaderView) {
            sectionIcon = getDrawable(R.styleable.SectionHeaderView_sectionIcon)
            sectionTitle = getString(R.styleable.SectionHeaderView_sectionTitle) ?: ""
        }
    }
}