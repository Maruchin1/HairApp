package com.example.care_details.use_case

import arrow.core.Either
import arrow.core.computations.either
import com.example.corev2.dao.CareStepDao
import com.example.corev2.entities.CareStep

internal class DeleteCareStepUseCase(
    private val actions: Actions,
    private val careStepDao: CareStepDao
) {

    suspend operator fun invoke(careStepToDelete: CareStep): Either<Fail, Unit> {
        return either {
            confirmCareStepDeletion().bind()
            deleteCareStepFromDb(careStepToDelete)
        }
    }

    private suspend fun confirmCareStepDeletion(): Either<Fail, Unit> {
        val confirmed = actions.confirmCareStepDeletion()
        return if (confirmed) Either.Right(Unit) else Either.Left(Fail.DeletionNotConfirmed)
    }

    private suspend fun deleteCareStepFromDb(careStep: CareStep) {
        careStepDao.delete(careStep)
    }

    sealed class Fail {
        object DeletionNotConfirmed : Fail()
    }

    interface Actions {
        suspend fun confirmCareStepDeletion(): Boolean
    }
}