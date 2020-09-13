package com.example.hairapp.framework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import com.example.hairapp.BR

fun AppCompatActivity.setStatusBarColor(colorId: Int) {
    window.statusBarColor = ContextCompat.getColor(this, colorId)
}

fun AppCompatActivity.setNavigationColor(colorId: Int) {
    window.navigationBarColor = ContextCompat.getColor(this, colorId)
}

fun <T : ViewDataBinding> AppCompatActivity.bind(layoutId: Int, viewModel: ViewModel?) {
    val binding = DataBindingUtil.setContentView<T>(this, layoutId)
    binding.lifecycleOwner = this
    binding.setVariable(BR.controller, this)
    viewModel?.let {
        binding.setVariable(BR.viewModel, viewModel)
    }
}

fun <T : ViewDataBinding> DialogFragment.bind(inflater: LayoutInflater, container: ViewGroup?, layoutId: Int, viewModel: ViewModel?): View {
    val binding = DataBindingUtil.inflate<T>(inflater, layoutId, container, false)
    binding.lifecycleOwner = this
    binding.setVariable(BR.controller, this)
    viewModel?.let {
        binding.setVariable(BR.viewModel, viewModel)
    }
    return binding.root
}