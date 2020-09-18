package com.example.hairapp.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import com.example.hairapp.R
import kotlinx.android.synthetic.main.view_toolbar.view.*

@BindingAdapter("app:title")
fun setTitle(view: ToolbarView, title: String?) {
    view.title = title
}

@BindingAdapter("app:onBackClick")
fun setOnBackClick(view: ToolbarView, action: () -> Unit) {
    view.onBackClick = action
}

@BindingAdapter("app:onActionClick")
fun setOnActionClick(view: ToolbarView, action: () -> Unit) {
    view.onActionClick = action
}

class ToolbarView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var title: String?
        get() = toolbar_title.text.toString()
        set(value) {
            toolbar_title.text = value
        }

    var actionIcon: Drawable?
        get() = toolbar_action_btn.drawable
        set(value) {
            toolbar_action_btn.setImageDrawable(value)
        }

    var onBackClick: (() -> Unit)? = null
        set(value) {
            field = value
            toolbar_back_btn.visibility = if (value != null) View.VISIBLE else View.GONE
        }

    var onActionClick: (() -> Unit)? = null
        set(value) {
            field = value
            toolbar_action_btn.visibility = if (value != null) View.VISIBLE else View.GONE
        }

    init {
        View.inflate(context, R.layout.view_toolbar, this)
        context.obtainStyledAttributes(attrs, R.styleable.ToolbarView).run {
            title = getString(R.styleable.ToolbarView_title)
            actionIcon = getDrawable(R.styleable.ToolbarView_actionIcon)
            recycle()
        }
        onBackClick = null
        onActionClick = null
        toolbar_back_btn.setOnClickListener {
            onBackClick?.invoke()
        }
        toolbar_action_btn.setOnClickListener {
            onActionClick?.invoke()
        }
    }
}