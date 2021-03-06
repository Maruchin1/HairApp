package com.example.hairapp.framework

import android.app.Activity
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

fun AppCompatActivity.showErrorSnackbar(message: String?) {
    val coordinator = findViewById<CoordinatorLayout>(R.id.coordinator)
    Snackbar
        .make(coordinator, message ?: "Wystąpił nieoczekiwany błąd", Snackbar.LENGTH_SHORT)
        .show()
}

suspend fun FragmentActivity.datePickerDialog(): LocalDate? = suspendCoroutine {
    val dialog = MaterialDatePicker.Builder
        .datePicker()
        .setTitleText("Wybierz datę")
        .setSelection(Instant.now().toEpochMilli())
        .build()
    dialog.addOnPositiveButtonClickListener { millis ->
        val date = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        it.resume(date)
    }
    dialog.addOnCancelListener { _ -> it.resume(null) }
    dialog.show(supportFragmentManager, "DatePicker")
}

suspend fun FragmentActivity.confirmDialog(
    title: String,
    message: String
): Boolean = suspendCoroutine {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("Tak") { _, _ -> it.resume(true) }
        .setNegativeButton("Nie") { _, _ -> it.resume(false) }
        .setOnCancelListener { _ -> it.resume(false) }
        .show()
}

suspend fun FragmentActivity.selectCareStepDialog(): CareStep.Type? = suspendCoroutine {
    val values = CareStep.Type.values()
    val items = values.map { Converter.careStepType(it) }
    MaterialAlertDialogBuilder(this)
        .setTitle("Rodzaj produktu")
        .setItems(items.toTypedArray()) { _, which ->
            val selectedValue = values[which]
            it.resume(selectedValue)
        }
        .setOnCancelListener { _ -> it.resume(null) }
        .show()
}

suspend fun FragmentActivity.selectCareSchemaDialog(
    schemas: List<CareSchema>
): CareSchema? = suspendCoroutine {
    val schemasWithEmpty = mutableListOf<CareSchema>().apply {
        addAll(schemas)
        add(CareSchema.noSchema)
    }
    val items = schemasWithEmpty.map { it.name }
    MaterialAlertDialogBuilder(this)
        .setTitle("Schemat pielęgnacji")
        .setItems(items.toTypedArray()) { _, which ->
            val selectedValue = schemasWithEmpty[which]
            it.resume(selectedValue)
        }
        .setOnCancelListener { _ -> it.resume(null) }
        .show()
}

suspend fun FragmentActivity.inputDialog(
    title: String
): String? = suspendCoroutine {
    val inflater = layoutInflater
    val view = inflater.inflate(R.layout.view_single_input, null)
    val editText: TextInputEditText = view.findViewById(R.id.input)
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setPositiveButton("Zapisz") { _, _ -> it.resume(editText.text?.toString()) }
        .setNeutralButton("Zamknij") { _, _ -> it.resume(null) }
        .setOnCancelListener { _ -> it.resume(null) }
        .setView(view)
        .show()
}