package com.example.edit_care_schema.use_case

import android.content.Context
import arrow.core.Either
import arrow.core.computations.either
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.relations.CareSchemaWithSteps

internal class DeleteSchemaStepUseCase(
    private val actions: Actions,
    private val careSchemaStepDao: CareSchemaStepDao,
    private val updateStepsOrderUseCase: UpdateStepsOrderUseCase
) {

    suspend operator fun invoke(
        context: Context,
        careSchemaWithSteps: CareSchemaWithSteps?,
        stepToDelete: CareSchemaStep
    ): Either<Fail, Unit> {
        if (careSchemaWithSteps == null) {
            return Either.Left(Fail.NoSchema)
        }
        return either {
            askForConfirmation(context, stepToDelete).bind()
            deleteStepFromDb(stepToDelete)
            updateStepsOrder(careSchemaWithSteps.steps, stepToDelete)
        }
    }

    private suspend fun askForConfirmation(
        context: Context,
        stepToDelete: CareSchemaStep
    ): Either<Fail, Unit> {
        val confirmed = actions.confirmStepDeletion(context, stepToDelete)
        return if (confirmed) {
            Either.Right(Unit)
        } else {
            Either.Left(Fail.DeletionNotConfirmed)
        }
    }

    private suspend fun deleteStepFromDb(stepToDelete: CareSchemaStep) {
        careSchemaStepDao.delete(stepToDelete)
    }

    private suspend fun updateStepsOrder(
        allSteps: List<CareSchemaStep>,
        stepToDelete: CareSchemaStep
    ) {
        val mutableSteps = allSteps.toMutableList()
        mutableSteps.removeIf { it.id == stepToDelete.id }
        updateStepsOrderUseCase(mutableSteps)
    }

    sealed class Fail {
        object NoSchema : Fail()
        object DeletionNotConfirmed : Fail()
    }

    interface Actions {
        suspend fun confirmStepDeletion(context: Context, stepToDelete: CareSchemaStep): Boolean
    }
}