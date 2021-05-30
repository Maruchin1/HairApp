package com.example.care_details.use_case

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.rightIfNotNull
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.Care

internal class ChangeCareNotesUseCase(
    private val careDao: CareDao
) {

    suspend operator fun invoke(careToUpdate: Care?, newNotes: String): Either<Fail, Unit> {
        return either {
            val care = careToUpdate.rightIfNotNull { Fail.NoCare }.bind()
            val update = care.copy(notes = newNotes)
            updateCareInDb(update)
        }
    }

    private suspend fun updateCareInDb(update: Care) {
        careDao.update(update)
    }

    sealed class Fail {
        object NoCare : Fail()
    }
}