package com.example.hairapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.hairapp.R
import com.example.hairapp.databinding.ViewStatCounterBinding

class StatCounterView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val binding = ViewStatCounterBinding.inflate(LayoutInflater.from(context), this, true)

    var value: String?
        get() = binding.value.text.toString()
        set(value) {
            binding.value.text = value
        }

    var label: String?
        get() = binding.label.text.toString()
        set(value) {
            binding.label.text = value
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.StatCounterView).run {
            value = getString(R.styleable.StatCounterView_value)
            label = getString(R.styleable.StatCounterView_label)
            recycle()
        }
    }
}