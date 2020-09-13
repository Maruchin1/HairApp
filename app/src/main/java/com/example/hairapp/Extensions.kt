package com.example.hairapp

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.hairapp.use_case.UseCase
import com.example.hairapp.use_case.UseCaseResult

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

suspend fun <O : Any> UseCase<Unit, O>.execute(): UseCaseResult<O> {
    return execute(Unit)
}