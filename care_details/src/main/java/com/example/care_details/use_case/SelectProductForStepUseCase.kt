package com.example.care_details.use_case

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.rightIfNotNull
import com.example.corev2.dao.CareStepDao
import com.example.corev2.entities.CareStep
import com.example.corev2.entities.Product

internal class SelectProductForStepUseCase(
    private val actions: Actions,
    private val careStepDao: CareStepDao
) {

    suspend operator fun invoke(careStepToUpdate: CareStep): Either<Fail, Unit> {
        return either {
            val selectedProductId = askForProductId(careStepToUpdate.productType).bind()
            val update = careStepToUpdate.copy(productId = selectedProductId)
            updateStepInDb(update)
        }
    }

    private suspend fun askForProductId(type: Product.Type): Either<Fail, Long> {
        return actions.askForProductId(type)
            .rightIfNotNull { Fail.ProductNotSelected }
    }

    private suspend fun updateStepInDb(update: CareStep) {
        careStepDao.update(update)
    }

    sealed class Fail {
        object ProductNotSelected : Fail()
    }

    interface Actions {
        suspend fun askForProductId(type: Product.Type): Long?
    }
}