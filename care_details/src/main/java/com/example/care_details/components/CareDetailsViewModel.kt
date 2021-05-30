package com.example.care_details.components

import androidx.lifecycle.*
import arrow.core.Either
import arrow.core.computations.either
import com.example.care_details.use_case.ChangeCareDateUseCase
import com.example.care_details.use_case.ChangeCareNotesUseCase
import com.example.care_details.use_case.DeleteCareUseCase
import com.example.care_details.use_case.SelectProductForStepUseCase
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.*
import com.example.corev2.relations.CareStepWithProduct
import com.example.corev2.service.ClockService
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime

internal class CareDetailsViewModel(
    private val careDao: CareDao,
    private val changeCareDateUseCase: ChangeCareDateUseCase,
    private val deleteCareUseCase: DeleteCareUseCase,
    private val selectProductForStepUseCase: SelectProductForStepUseCase,
    private val changeCareNotesUseCase: ChangeCareNotesUseCase
) : ViewModel() {

    private val careIdState = MutableStateFlow<Long?>(null)
    private val careFlow = careIdState
        .filterNotNull()
        .flatMapLatest { careDao.getById(it) }
        .filterNotNull()
    private val notesEditModeState = MutableStateFlow(false)

    val careDate: LiveData<LocalDateTime> = careFlow
        .mapLatest { it.care.date }
        .asLiveData()
    val schemaName: LiveData<String> = careFlow
        .mapLatest { it.care.schemaName }
        .asLiveData()
    val steps: LiveData<List<CareStepWithProduct>> = careFlow
        .mapLatest { it.steps }
        .asLiveData()
    val notes: LiveData<String> = careFlow
        .mapLatest { it.care.notes }
        .asLiveData()
    val notesNotAvailable: LiveData<Boolean> = notes
        .map { it.isEmpty() }
    val photos: LiveData<List<CarePhoto>> = careFlow
        .mapLatest { it.photos }
        .asLiveData()
    val notesEditMode: LiveData<Boolean> = notesEditModeState.asLiveData()

    fun onCareSelected(careId: Long) = viewModelScope.launch {
        careIdState.emit(careId)
    }

    suspend fun onChangeDateClicked(): Either<ChangeCareDateUseCase.Fail, Unit> {
        return changeCareDateUseCase(careFlow.firstOrNull()?.care)
    }

    suspend fun onDeleteCareClicked(): Either<DeleteCareUseCase.Fail, Unit> {
        return deleteCareUseCase(careFlow.firstOrNull()?.care)
    }

    suspend fun onCareStepClicked(step: CareStep): Either<SelectProductForStepUseCase.Fail, Unit> {
        return selectProductForStepUseCase(step)
    }

    fun onEditNotesClicked() = viewModelScope.launch {
        notesEditModeState.emit(true)
    }

    fun onCancelNotesEditionClicked() = viewModelScope.launch {
        notesEditModeState.emit(false)
    }

    suspend fun onSaveNotesClicked(newNotes: String): Either<ChangeCareNotesUseCase.Fail, Unit> {
        return either {
            changeCareNotesUseCase(careFlow.firstOrNull()?.care, newNotes).bind()
            notesEditModeState.emit(false)
        }
    }
}