package com.example.corev2.ui

import android.content.Context
import android.view.LayoutInflater
import com.example.corev2.R
import com.example.corev2.databinding.ViewSingleInputBinding
import com.example.corev2.entities.Product
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DialogService {

    suspend fun confirm(
        context: Context,
        title: String,
        message: String? = null
    ): Boolean = suspendCoroutine {
        MaterialAlertDialogBuilder(context).apply {
            setTitle(title)
            message?.let { setMessage(it) }
            setPositiveButton(R.string.confirm) { _, _ ->
                it.resume(true)
            }
            setNegativeButton(R.string.cancel) { _, _ ->
                it.resume(false)
            }
            setOnCancelListener { _ ->
                it.resume(false)
            }
        }.show()

    }

    suspend fun typeText(
        context: Context,
        title: String,
        currentValue: String? = null
    ): String? = suspendCoroutine {
        val inflater = LayoutInflater.from(context)
        val binding = ViewSingleInputBinding.inflate(inflater, null, false)
        currentValue?.let { binding.input.setText(it) }
        MaterialAlertDialogBuilder(context).apply {
            setTitle(title)
            setPositiveButton(R.string.save) { _, _ ->
                it.resume(binding.input.text?.toString())
            }
            setNeutralButton(R.string.close) { _, _ ->
                it.resume(null)
            }
            setOnCancelListener { _ ->
                it.resume(null)
            }
            setView(binding.root)
        }.show()
    }

    suspend fun selectProductType(
        context: Context
    ): Product.Type? = suspendCoroutine {
        val values = Product.Type.values()
        val items = values.map { context.getString(it.resId) }
        MaterialAlertDialogBuilder(context).apply {
            setTitle(R.string.product_type)
            setItems(items.toTypedArray()) { _, which ->
                val selectedValue = values[which]
                it.resume(selectedValue)
            }
            setOnCancelListener { _ ->
                it.resume(null)
            }
        }.show()
    }
}