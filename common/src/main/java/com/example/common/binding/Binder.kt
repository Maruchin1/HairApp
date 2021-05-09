package com.example.common.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.MaterialToolbar

object Binder {

    @BindingAdapter("app:onNavigationClick")
    @JvmStatic
    fun setOnNavigationClick(view: MaterialToolbar, action: (() -> Unit)?) {
        view.setNavigationOnClickListener { action?.invoke() }
    }
}