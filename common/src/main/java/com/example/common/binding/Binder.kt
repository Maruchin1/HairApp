package com.example.common.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.MaterialToolbar

object Binder {

    @BindingAdapter("app:onNavigationClick")
    @JvmStatic
    fun setOnNavigationClick(view: MaterialToolbar, action: (() -> Unit)?) {
        view.setNavigationOnClickListener { action?.invoke() }
    }

    @BindingAdapter("app:onLongClick")
    @JvmStatic
    fun setOnLongClick(view: View, action: (() -> Unit)?) {
        view.setOnLongClickListener {
            action?.invoke()
            true
        }
    }

    @BindingAdapter("app:visibleOrGone")
    @JvmStatic
    fun setVisibleOrGone(view: View, visible: Boolean?) {
        view.visibility = if (visible == true) View.VISIBLE else View.GONE
    }
}