package com.example.care_details.use_case

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.rightIfNotNull
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.Care
import java.time.LocalDateTime

internal class ChangeCareDateUseCase(
    private val actions: Actions,
    private val careDao: CareDao
) {

    suspend operator fun invoke(careToUpdate: Care?): Either<Fail, Unit> {
        return either {
            val care = careToUpdate.rightIfNotNull { Fail.NoCare }.bind()
            val newDate = askForNewDate(care).bind()
            val update = care.copy(date = newDate)
            updateCareInDb(update)
        }
    }

    private suspend fun askForNewDate(care: Care): Either<Fail, LocalDateTime> {
        return actions.askForNewDate(care.date)
            ?.let { Either.Right(it) }
            ?: Either.Left(Fail.NewDateNotSelected)
    }

    private suspend fun updateCareInDb(update: Care) {
        careDao.update(update)
    }

    sealed class Fail {
        object NoCare : Fail()
        object NewDateNotSelected : Fail()
    }

    interface Actions {
        suspend fun askForNewDate(selectedDate: LocalDateTime): LocalDateTime?
    }
}