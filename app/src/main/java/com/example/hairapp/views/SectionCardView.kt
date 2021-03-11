package com.example.hairapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.hairapp.R
import com.example.hairapp.databinding.ViewSectionCardBinding
import kotlinx.android.synthetic.main.view_section_card.view.*

class SectionCardView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val binding = ViewSectionCardBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String?
        get() = binding.sectionTitle.text.toString()
        set(value) {
            binding.sectionTitle.text = value
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.SectionCardView).run {
            title = getString(R.styleable.SectionCardView_titleResId)
            recycle()
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (section_body == null) {
            super.addView(child, index, params)
        } else {
            section_body.addView(child, index, params)
        }
    }
}