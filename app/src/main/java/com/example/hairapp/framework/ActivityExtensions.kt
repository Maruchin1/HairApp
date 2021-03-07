package com.example.hairapp.framework

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.core.domain.CareSchema
import com.example.core.domain.CareStep
import com.example.hairapp.BR
import com.example.hairapp.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun Activity.setSystemColors(colorResId: Int) {
    window.statusBarColor = ContextCompat.getColor(this, colorResId)
    window.navigationBarColor = ContextCompat.getColor(this, colorResId)
}

fun Activity.setSystemColors(statusBar: Int, navigation: Int) {
    window.statusBarColor = ContextCompat.getColor(this, statusBar)
    window.navigationBarColor = ContextCompat.getColor(this, navigation)
}

fun <T : ViewDataBinding> AppCompatActivity.bind(layoutId: Int, viewModel: ViewModel?): T {
    val binding = DataBindingUtil.setContentView<T>(this, layoutId)
    binding.lifecycleOwner = this
    binding.setVariable(BR.controller, this)
    viewModel?.let {
        binding.setVariable(BR.viewModel, viewModel)
    }
    return binding
}
