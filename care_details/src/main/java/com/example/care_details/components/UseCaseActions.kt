package com.example.care_details.components

import androidx.appcompat.app.AppCompatActivity
import arrow.core.computations.nullable
import com.example.care_details.use_case.ChangeCareDateUseCase
import com.example.care_details.use_case.DeleteCareUseCase
import com.example.care_details.use_case.SelectProductForStepUseCase
import com.example.corev2.entities.Product
import com.example.corev2.ui.DialogService
import java.lang.ref.WeakReference
import java.time.LocalDateTime

internal class UseCaseActions(
    private val dialogService: DialogService
) : ChangeCareDateUseCase.Actions,
    DeleteCareUseCase.Actions,
    SelectProductForStepUseCase.Actions {

    private lateinit var activityRef: WeakReference<AppCompatActivity>

    fun bindToActivity(activity: AppCompatActivity) {
        activityRef = WeakReference(activity)
    }

    override suspend fun askForNewDate(selectedDate: LocalDateTime): LocalDateTime? {
        return nullable {
            val activity = activityRef.get().bind()
            dialogService.selectDateTime(activity.supportFragmentManager, selectedDate).bind()
        }
    }

    override suspend fun confirmCareDeletion(): Boolean {
        return activityRef.get()?.let {
            dialogService.confirm(
                context = it,
                title = "Usunąć pielęgnację?"
            )
        } ?: false
    }

    override suspend fun askForProduct(type: Product.Type): Product? {
        return null
    }
}