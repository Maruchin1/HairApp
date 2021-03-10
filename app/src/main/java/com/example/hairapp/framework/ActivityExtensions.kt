package com.example.hairapp.framework

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.hairapp.BR

fun <T : ViewDataBinding> AppCompatActivity.bindActivity(
    layoutId: Int,
    viewModel: ViewModel? = null
): T {
    return DataBindingUtil.setContentView<T>(this, layoutId).apply {
        lifecycleOwner = this@bindActivity
        setVariable(BR.controller, this@bindActivity)
        viewModel?.let {
            setVariable(BR.viewModel, it)
        }
    }
}
