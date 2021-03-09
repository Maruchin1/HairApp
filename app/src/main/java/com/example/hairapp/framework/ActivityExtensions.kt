package com.example.hairapp.framework

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.hairapp.BR

fun Activity.setSystemColors(colorResId: Int) {
    window.statusBarColor = ContextCompat.getColor(this, colorResId)
    window.navigationBarColor = ContextCompat.getColor(this, colorResId)
}

fun Activity.setSystemColors(statusBar: Int, navigation: Int) {
    window.statusBarColor = ContextCompat.getColor(this, statusBar)
    window.navigationBarColor = ContextCompat.getColor(this, navigation)
}

fun <T : ViewDataBinding> AppCompatActivity.bindActivity(layoutId: Int, viewModel: ViewModel?): T {
    return DataBindingUtil.setContentView<T>(this, layoutId).apply {
        lifecycleOwner = this@bindActivity
        setVariable(BR.controller, this@bindActivity)
        viewModel?.let {
            setVariable(BR.viewModel, it)
        }
    }
}
