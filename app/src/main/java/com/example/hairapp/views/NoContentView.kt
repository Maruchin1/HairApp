package com.example.hairapp.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import com.example.hairapp.R
import com.example.hairapp.framework.Binder
import kotlinx.android.synthetic.main.view_no_content.view.*

class NoContentView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var icon: Drawable?
        get() = view_no_content_icon.drawable
        set(value) {
            view_no_content_icon.setImageDrawable(value)
        }

    var title: String?
        get() = view_no_content_title.text.toString()
        set(value) {
            view_no_content_title.text = value
        }

    var onActionClick: (() -> Unit)? = null
        set(value) {
            field = value
            updateActionVisibility()
        }

    init {
        inflate(context, R.layout.view_no_content_message, this)
        context.obtainStyledAttributes(attrs, R.styleable.NoContentView).run {
            icon = getDrawable(R.styleable.NoContentView_icon)
            title = getString(R.styleable.NoContentView_title)
            recycle()
        }
        updateActionVisibility()
        view_no_content_action.setOnClickListener { onActionClick?.invoke() }
    }

    private fun updateActionVisibility() {
        Binder.setVisibleOrGone(view_no_content_action, onActionClick != null)
    }

    companion object {

        @BindingAdapter("app:onActionClick")
        @JvmStatic
        fun setOnAction(view: NoContentView, action: (() -> Unit)?) {
            view.onActionClick = action
        }
    }
}