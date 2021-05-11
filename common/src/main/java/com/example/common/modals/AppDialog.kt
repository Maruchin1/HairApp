package com.example.common.modals

import com.example.common.binding.Converter
import com.example.core.domain.CareSchema
import com.example.core.domain.CareStep
import com.example.core.domain.CaresLimit
import android.content.Context
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.fragment.app.FragmentManager
import com.example.common.databinding.ViewSingleInputBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppDialog {

    suspend fun pickNumber(
        context: Context,
        title: String,
        minValue: Int,
        maxValue: Int,
        currentValue: Int
    ): Int? = suspendCoroutine {
        val numberPicker = NumberPicker(context)
        numberPicker.minValue = minValue
        numberPicker.maxValue = maxValue
        numberPicker.value = currentValue

        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setView(numberPicker)
            .setPositiveButton("Zapisz") { _, _ ->
                it.resume(numberPicker.value)
            }
            .setNeutralButton("Zamknij") { _, _ ->
                it.resume(null)
            }
            .setOnCancelListener { _ ->
                it.resume(null)
            }
            .show()
    }

    suspend fun pickDate(manager: FragmentManager): LocalDate? = suspendCoroutine {
        val dialog = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText("Wybierz datę")
            .setSelection(Instant.now().toEpochMilli())
            .build()
        dialog.run {
            addOnPositiveButtonClickListener { millis ->
                val date = Instant.ofEpochMilli(millis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                it.resume(date)
            }
            addOnCancelListener { _ ->
                it.resume(null)
            }
            show(manager, "DatePicker")
        }
    }

    suspend fun confirm(
        context: Context,
        title: String,
        message: String
    ): Boolean = suspendCoroutine {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Tak") { _, _ ->
                it.resume(true)
            }
            .setNegativeButton("Nie") { _, _ ->
                it.resume(false)
            }
            .setOnCancelListener { _ ->
                it.resume(false)
            }
            .show()
    }

    suspend fun confirm(
        context: Context,
        title: String
    ): Boolean = suspendCoroutine {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setPositiveButton("Potwierdź") { _, _ ->
                it.resume(true)
            }
            .setNegativeButton("Anuluj") { _, _ ->
                it.resume(false)
            }
            .setOnCancelListener { _ ->
                it.resume(false)
            }
            .show()
    }

    suspend fun selectCareStepType(context: Context): CareStep.Type? = suspendCoroutine {
        val values = CareStep.Type.values()
        val items = values.map { Converter.careStepType(it) }
        MaterialAlertDialogBuilder(context)
            .setTitle("Rodzaj produktu")
            .setItems(items.toTypedArray()) { _, which ->
                val selectedValue = values[which]
                it.resume(selectedValue)
            }
            .setOnCancelListener { _ ->
                it.resume(null)
            }
            .show()
    }

    suspend fun selectCareSchema(
        context: Context,
        schemas: List<CareSchema>
    ): CareSchema? = suspendCoroutine {
        val schemasWithEmpty = mutableListOf<CareSchema>().apply {
            addAll(schemas)
            add(CareSchema.noSchema)
        }
        val items = schemasWithEmpty.map { it.name }.toTypedArray()
        MaterialAlertDialogBuilder(context)
            .setTitle("Schemat pielęgnacji")
            .setItems(items) { _, which ->
                it.resume(schemasWithEmpty[which])
            }
            .setOnCancelListener { _ ->
                it.resume(null)
            }
            .show()
    }

    suspend fun selectCaresForBalance(
        context: Context,
        currentValue: CaresLimit
    ): CaresLimit? = suspendCoroutine {
        val values = CaresLimit.values()
        val items = values.map { Converter.caresForBalance(it) }.toTypedArray()
        val selectedIdx = values.indexOf(currentValue)
        MaterialAlertDialogBuilder(context)
            .setTitle("Pielęgnacje")
            .setSingleChoiceItems(items, selectedIdx) { dialog, which ->
                it.resume(values[which])
                dialog.dismiss()
            }
            .setOnCancelListener { _ ->
                it.resume(null)
            }
            .show()
    }

    suspend fun typeText(
        context: Context,
        title: String,
        currentValue: String? = null
    ): String? = suspendCoroutine {
        val inflater = LayoutInflater.from(context)
        val binding = ViewSingleInputBinding.inflate(inflater, null, false)
        currentValue?.let { binding.input.setText(it) }
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setPositiveButton("Zapisz") { _, _ ->
                it.resume(binding.input.text?.toString())
            }
            .setNeutralButton("Zamknij") { _, _ ->
                it.resume(null)
            }
            .setOnCancelListener { _ ->
                it.resume(null)
            }
            .setView(binding.root)
            .show()
    }

//    fun pickImage(activity: Activity) {
//        ImagePicker.with(activity)
//            .crop(x = 1f, y = 1f)
//            .compress(maxSize = 1024)
//            .maxResultSize(width = 1080, height = 1080)
//            .start()
//    }
}