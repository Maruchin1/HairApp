package com.example.care_details.use_case

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.rightIfNotNull
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.Care

internal class DeleteCareUseCase(
    private val actions: Actions,
    private val careDao: CareDao
) {

    suspend operator fun invoke(careToDelete: Care?): Either<Fail, Unit> {
        return either {
            val care = careToDelete.rightIfNotNull { Fail.NoCare }.bind()
            confirmDeletion().bind()
            deleteCareFromDb(care)
        }
    }

    private suspend fun confirmDeletion(): Either<Fail, Unit> {
        val confirmed = actions.confirmCareDeletion()
        return if (confirmed) {
            Either.Right(Unit)
        } else {
            Either.Left(Fail.DeletionNotConfirmed)
        }
    }

    private suspend fun deleteCareFromDb(care: Care) {
        careDao.delete(care)
    }

    sealed class Fail {
        object NoCare : Fail()
        object DeletionNotConfirmed : Fail()
    }

    interface Actions {
        suspend fun confirmCareDeletion(): Boolean
    }
}