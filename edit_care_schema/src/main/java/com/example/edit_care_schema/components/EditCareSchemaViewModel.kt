package com.example.edit_care_schema.components

import android.content.Context
import androidx.lifecycle.*
import arrow.core.Either
import arrow.core.computations.either
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.corev2.ui.DialogService
import com.example.edit_care_schema.use_case.AddSchemaStepUseCase
import com.example.edit_care_schema.use_case.ChangeSchemaNameUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaUseCase
import kotlinx.coroutines.flow.*

internal class EditCareSchemaViewModel(
    private val careSchemaDao: CareSchemaDao,
    private val careSchemaStepDao: CareSchemaStepDao,
    private val dialogService: DialogService,
    private val changeSchemaNameUseCase: ChangeSchemaNameUseCase,
    private val deleteSchemaUseCase: DeleteSchemaUseCase,
    private val addSchemaStepUseCase: AddSchemaStepUseCase
) : ViewModel() {

    private val careSchemaId = MutableStateFlow<Long?>(null)

    private val careSchemaWithStepsFlow: Flow<CareSchemaWithSteps?> = careSchemaId
        .filterNotNull()
        .flatMapLatest { careSchemaDao.getById(it) }

    val schemaName: LiveData<String> = careSchemaWithStepsFlow
        .filterNotNull()
        .map { it.careSchema.name }
        .asLiveData()

    val schemaSteps: LiveData<List<CareSchemaStep>> = careSchemaWithStepsFlow
        .filterNotNull()
        .map { it.steps }
        .map { getSortedByOrder(it) }
        .asLiveData()

    val noSteps: LiveData<Boolean> = schemaSteps.map { it.isEmpty() }

    suspend fun selectSchema(careSchemaId: Long) {
        this.careSchemaId.emit(careSchemaId)
    }

    suspend fun changeSchemaName(
        context: Context
    ): Either<ChangeSchemaNameUseCase.Fail, Unit> = either {
        val careSchema = careSchemaWithStepsFlow.firstOrNull()?.careSchema
        changeSchemaNameUseCase(context, careSchema).bind()
    }

    suspend fun deleteSchema(
        context: Context
    ): Either<DeleteSchemaUseCase.Fail, Unit> = either {
        val careSchema = careSchemaWithStepsFlow.firstOrNull()?.careSchema
        deleteSchemaUseCase(context, careSchema).bind()
    }

    suspend fun addStep(context: Context): Either<AddSchemaStepUseCase.Fail, Unit> = either {
        val careSchemaWithSteps = careSchemaWithStepsFlow.firstOrNull()
        addSchemaStepUseCase(context, careSchemaWithSteps).bind()
    }

    suspend fun updateSteps(steps: List<CareSchemaStep>) {
        careSchemaStepDao.update(*steps.toTypedArray())
    }

    suspend fun deleteStep(context: Context, step: CareSchemaStep) {
        dialogService.confirm(
            context = context,
            title = "Usunąć krok ${step.order + 1} ${context.getString(step.productType.resId)}?"
        ).let { confirmed ->
            if (confirmed) {
                careSchemaStepDao.delete(step)
                withCurrentCareSchema {
                    val mutableSteps = it.steps.toMutableList()
                    mutableSteps.remove(step)
                    mutableSteps.forEachIndexed { index, careSchemaStep ->
                        careSchemaStep.order = index
                    }
                    careSchemaStepDao.update(*mutableSteps.toTypedArray())
                }
            }
        }
    }

    private suspend fun withCurrentCareSchema(action: suspend (CareSchemaWithSteps) -> Unit) {
        careSchemaWithStepsFlow
            .firstOrNull()
            ?.let { action(it) }
    }

    private fun getSortedByOrder(steps: List<CareSchemaStep>): List<CareSchemaStep> {
        return steps.sortedBy { it.order }
    }
}