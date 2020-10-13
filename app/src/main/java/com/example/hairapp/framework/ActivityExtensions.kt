package com.example.hairapp.framework

import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.core.domain.CareStep
import com.example.hairapp.BR
import com.example.hairapp.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

fun AppCompatActivity.showErrorSnackbar(message: String?) {
    val coordinator = findViewById<CoordinatorLayout>(R.id.coordinator)
    Snackbar
        .make(coordinator, message ?: "Wystąpił nieoczekiwany błąd", Snackbar.LENGTH_SHORT)
        .show()
}

inline fun FragmentActivity.datePickerDialog(crossinline selected: (LocalDate) -> Unit) {
    val dialog = MaterialDatePicker.Builder
        .datePicker()
        .setTitleText("Wybierz datę")
        .setSelection(Instant.now().toEpochMilli())
        .build()
    dialog.addOnPositiveButtonClickListener {
        val date = Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        selected(date)
    }
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
        .show()
}

fun FragmentActivity.actionDialog(
    title: String,
    items: List<Pair<String, () -> Unit>>
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setItems(items.map { it.first }.toTypedArray()) { _, which ->
            val action = items[which].second
            action()
        }
        .show()
}

inline fun FragmentActivity.selectCareStepDialog(
    crossinline careStepAction: (CareStep.Type) -> Unit
) = actionDialog(
    title = "Rodzaj produktu",
    items = CareStep.Type.values().map {
        Pair(
            first = Converter.careStepType(it)!!,
            second = { careStepAction(it) }
        )
    }
)