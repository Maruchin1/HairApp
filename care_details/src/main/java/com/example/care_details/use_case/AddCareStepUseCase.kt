package com.example.care_details.use_case

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.rightIfNotNull
import com.example.corev2.dao.CareStepDao
import com.example.corev2.entities.CareStep
import com.example.corev2.relations.CareWithStepsAndPhotos

internal class AddCareStepUseCase(
    private val actions: Actions,
    private val careStepDao: CareStepDao
) {

    suspend operator fun invoke(careToAddStep: CareWithStepsAndPhotos?): Either<Fail, Unit> {
        return either {
            val care = careToAddStep.rightIfNotNull { Fail.NoCare }.bind()
            val selectedProductId = askForProductId().bind()
            val newStep = createNewStep(care, selectedProductId)
            insertStepToDb(newStep)
        }
    }

    private suspend fun askForProductId(): Either<Fail, Long> {
        return actions.askForProductId()
            .rightIfNotNull { Fail.ProductNotSelected }
    }

    private fun createNewStep(care: CareWithStepsAndPhotos, selectedProductId: Long) = CareStep(
        productType = null,
        order = care.steps.size,
        productId = selectedProductId,
        careId = care.care.id
    )

    private suspend fun insertStepToDb(step: CareStep) {
        careStepDao.insert(step)
    }

    sealed class Fail {
        object NoCare : Fail()
        object ProductNotSelected : Fail()
    }

    interface Actions {
        suspend fun askForProductId(): Long?
    }
}