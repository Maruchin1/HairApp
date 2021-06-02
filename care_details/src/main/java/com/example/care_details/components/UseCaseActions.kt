package com.example.care_details.components

import androidx.appcompat.app.AppCompatActivity
import com.example.care_details.use_case.*
import com.example.care_details.use_case.AddCareStepUseCase
import com.example.care_details.use_case.ChangeCareDateUseCase
import com.example.care_details.use_case.DeleteCareUseCase
import com.example.care_details.use_case.SelectProductForStepUseCase
import com.example.corev2.entities.Product
import com.example.corev2.navigation.CaptureCarePhotoDestination
import com.example.corev2.navigation.SelectProductDestination
import com.example.corev2.ui.DialogService
import java.lang.ref.WeakReference
import java.time.LocalDateTime

internal class UseCaseActions(
    private val dialogService: DialogService,
    private val selectProductDestination: SelectProductDestination,
    private val captureCarePhotoDestination: CaptureCarePhotoDestination
) : ChangeCareDateUseCase.Actions,
    DeleteCareUseCase.Actions,
    SelectProductForStepUseCase.Actions,
    AddCareStepUseCase.Actions,
    DeleteCareStepUseCase.Actions,
    AddCarePhotoUseCase.Actions {

    private lateinit var activityRef: WeakReference<AppCompatActivity>

    fun bindToActivity(activity: AppCompatActivity) {
        activityRef = WeakReference(activity)
    }

    override suspend fun askForNewDate(selectedDate: LocalDateTime): LocalDateTime? {
        return activityRef.get()?.let {
            dialogService.selectDateTime(it.supportFragmentManager, selectedDate)
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

    override suspend fun askForProductId(type: Product.Type): Long? {
        return activityRef.get()?.let {
            val params = SelectProductDestination.Params(type)
            selectProductDestination.navigate(it, params)
        }
    }

    override suspend fun askForProductId(): Long? {
        return activityRef.get()?.let {
            val params = SelectProductDestination.Params(null)
            selectProductDestination.navigate(it, params)
        }
    }

    override suspend fun confirmCareStepDeletion(): Boolean {
        return activityRef.get()?.let {
            dialogService.confirm(
                context = it,
                title = "usunąć wybrany krok z pielęgnacji?"
            )
        } ?: false
    }

    override suspend fun captureNewCarePhoto(): String? {
        return activityRef.get()?.let {
            captureCarePhotoDestination.navigate(it)
        }
    }
}