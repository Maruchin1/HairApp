package com.example.care_details.use_case

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.rightIfNotNull
import com.example.corev2.dao.CarePhotoDao
import com.example.corev2.entities.Care
import com.example.corev2.entities.CarePhoto

internal class AddCarePhotoUseCase(
    private val actions: Actions,
    private val carePhotoDao: CarePhotoDao
) {

    suspend operator fun invoke(careToAdd: Care?): Either<Fail, Unit> {
        return either {
            val care = careToAdd.rightIfNotNull { Fail.NoCare }.bind()
            val newPhotoData = captureNewCarePhoto().bind()
            val newPhoto = createNewCarePhoto(newPhotoData, care.id)
            insertCarePhotoToDb(newPhoto)
        }
    }

    private suspend fun captureNewCarePhoto(): Either<Fail, String> {
        return actions.captureNewCarePhoto()
            .rightIfNotNull { Fail.PhotoNotCaptured }
    }

    private fun createNewCarePhoto(photoData: String, careId: Long) = CarePhoto(
        data = photoData,
        careId = careId
    )

    private suspend fun insertCarePhotoToDb(carePhoto: CarePhoto) {
        carePhotoDao.insert(carePhoto)
    }

    sealed class Fail {
        object NoCare : Fail()
        object PhotoNotCaptured : Fail()
    }

    interface Actions {
        suspend fun captureNewCarePhoto(): String?
    }
}