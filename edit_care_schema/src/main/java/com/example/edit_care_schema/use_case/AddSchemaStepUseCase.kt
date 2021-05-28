package com.example.edit_care_schema.use_case

import android.content.Context
import arrow.core.Either
import arrow.core.computations.either
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.Product
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.corev2.ui.DialogService

internal class AddSchemaStepUseCase(
    private val dialogService: DialogService,
    private val careSchemaStepDao: CareSchemaStepDao
) {

    suspend operator fun invoke(
        context: Context,
        careSchemaWithSteps: CareSchemaWithSteps?
    ): Either<Fail, Unit> {
        if (careSchemaWithSteps == null) {
            return Either.Left(Fail.NoCareSchema)
        }
        return either {
            val productType = askForProductType(context).bind()
            val newStep = createNewStep(careSchemaWithSteps, productType)
            careSchemaStepDao.insert(newStep)
        }
    }

    private suspend fun askForProductType(context: Context): Either<Fail, Product.Type> {
        val selectedType = dialogService.selectProductType(context)
        return if (selectedType == null) {
            Either.Left(Fail.ProductTypeNotSelected);
        } else {
            Either.Right(selectedType)
        }
    }

    private fun createNewStep(
        careSchemaWithSteps: CareSchemaWithSteps,
        productType: Product.Type
    ) = CareSchemaStep(
        productType = productType,
        order = careSchemaWithSteps.steps.size,
        careSchemaId = careSchemaWithSteps.careSchema.id
    )

    sealed class Fail {
        object NoCareSchema : Fail()
        object ProductTypeNotSelected : Fail()
    }
}