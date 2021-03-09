package com.example.hairapp.framework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.hairapp.BR
import com.example.hairapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

fun <T : ViewDataBinding> Fragment.bindActivity(
    inflater: LayoutInflater,
    container: ViewGroup?,
    layoutId: Int,
    viewModel: ViewModel?
): View {
    val binding = DataBindingUtil.inflate<T>(inflater, layoutId, container, false)
    binding.lifecycleOwner = this
    binding.setVariable(BR.controller, this)
    viewModel?.let {
        binding.setVariable(BR.viewModel, viewModel)
    }
    return binding.root
}