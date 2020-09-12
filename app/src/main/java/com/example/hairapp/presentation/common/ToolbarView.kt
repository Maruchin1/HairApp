package com.example.hairapp.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import com.example.hairapp.R
import kotlinx.android.synthetic.main.view_toolbar.view.*

@BindingAdapter("app:onBackClick")
fun setOnBackClick(view: ToolbarView, action: () -> Unit) {
    view.onBackClick = action
}

class ToolbarView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var title: String?
        get() = toolbar_title.text.toString()
        set(value) {
            toolbar_title.text = value
        }

    var onBackClick: (() -> Unit)? = null
        set(value) {
            field = value
            toolbar_back_btn.visibility = if (value != null) View.VISIBLE else View.GONE
        }

    init {
        View.inflate(context, R.layout.view_toolbar, this)
        context.obtainStyledAttributes(attrs, R.styleable.ToolbarView).run {
            title = getString(R.styleable.ToolbarView_title)
            recycle()
        }
        onBackClick = null
        toolbar_back_btn.setOnClickListener {
            onBackClick?.invoke()
        }
    }
}