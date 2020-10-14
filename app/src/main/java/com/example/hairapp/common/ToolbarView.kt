package com.example.hairapp.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.hairapp.R
import kotlinx.android.synthetic.main.view_toolbar.view.*

class ToolbarView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var title: String?
        get() = toolbar_title.text.toString()
        set(value) {
            toolbar_title.text = value
        }

    var navigationMode: Int = MODE_STANDARD
        set(value) {
            field = value
            val iconResId = when (value) {
                MODE_HOME -> R.drawable.ic_round_menu_24
                MODE_STANDARD -> R.drawable.ic_round_arrow_back_ios_24
                else -> throw IllegalStateException("Not matching navigation mode $value")
            }
            toolbar_toolbar.navigationIcon = ContextCompat.getDrawable(context, iconResId)
        }

    @MenuRes
    var optionsMenuResId: Int = NO_MENU
        set(value) {
            field = value
            if (value != NO_MENU) {
                toolbar_toolbar.inflateMenu(value)
            }
        }

    var onNavigationClick: (() -> Unit)? = null

    var onMenuOptionClick: ((MenuItem) -> Unit)? = null

    init {
        View.inflate(context, R.layout.view_toolbar, this)
        context.obtainStyledAttributes(attrs, R.styleable.ToolbarView).run {
            title = getString(R.styleable.ToolbarView_title)
            navigationMode = getInteger(R.styleable.ToolbarView_toolbarMode, MODE_STANDARD)
            optionsMenuResId = getResourceId(R.styleable.ToolbarView_optionsMenu, NO_MENU)
            recycle()
        }

        toolbar_toolbar.setNavigationOnClickListener { onNavigationClick?.invoke() }
        toolbar_toolbar.setOnMenuItemClickListener {
            onMenuOptionClick?.invoke(it)
            true
        }
    }

    companion object {
        private const val NO_MENU = -1
        private const val MODE_HOME = 0
        private const val MODE_STANDARD = 1

        @BindingAdapter("app:title")
        @JvmStatic
        fun setTitle(view: ToolbarView, title: String?) {
            view.title = title
        }

        @BindingAdapter("app:onNavigationClick")
        @JvmStatic
        fun setOnNavigationClick(view: ToolbarView, action: () -> Unit) {
            view.onNavigationClick = action
        }
    }

}