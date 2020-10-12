package com.example.hairapp.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import com.example.hairapp.R
import kotlinx.android.synthetic.main.view_toolbar.view.*

class ToolbarView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var title: String?
        get() = toolbar_title.text.toString()
        set(value) {
            toolbar_title.text = value
        }

    var leftActionIcon: Drawable?
        get() = toolbara_left_action.drawable
        set(value) {
            toolbara_left_action.setImageDrawable(value)
        }

    var rightActionIcon: Drawable?
        get() = toolbar_right_action.drawable
        set(value) {
            toolbar_right_action.setImageDrawable(value)
        }

    var onLeftAction: (() -> Unit)? = null
        set(value) {
            field = value
            toolbara_left_action.visibility = if (value != null) View.VISIBLE else View.GONE
        }

    var onRightAction: (() -> Unit)? = null
        set(value) {
            field = value
            toolbar_right_action.visibility = if (value != null) View.VISIBLE else View.GONE
        }

    init {
        View.inflate(context, R.layout.view_toolbar, this)
        context.obtainStyledAttributes(attrs, R.styleable.ToolbarView).run {
            title = getString(R.styleable.ToolbarView_title)
            leftActionIcon = getDrawable(R.styleable.ToolbarView_leftActionIcon)
            rightActionIcon = getDrawable(R.styleable.ToolbarView_rightActionIcon)
            recycle()
        }
        onLeftAction = null
        onRightAction = null
        toolbara_left_action.setOnClickListener {
            onLeftAction?.invoke()
        }
        toolbar_right_action.setOnClickListener {
            onRightAction?.invoke()
        }
    }

    companion object {
        @BindingAdapter("app:title")
        @JvmStatic
        fun setTitle(view: ToolbarView, title: String?) {
            view.title = title
        }

        @BindingAdapter("app:onLeftAction")
        @JvmStatic
        fun setOnLeftAction(view: ToolbarView, action: () -> Unit) {
            view.onLeftAction = action
        }

        @BindingAdapter("app:onRightAction")
        @JvmStatic
        fun setOnRightAction(view: ToolbarView, action: () -> Unit) {
            view.onRightAction = action
        }
    }

}