package com.example.cares_list.use_case

import android.app.Activity
import android.content.Context
import arrow.core.Either
import arrow.core.computations.either
import com.example.corev2.dao.CareDao
import com.example.corev2.dao.CareStepDao
import com.example.corev2.entities.Care
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareStep
import com.example.corev2.navigation.CareDetailsDestination
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.corev2.service.ClockService

internal class AddNewCareUseCase(
    private val actions: Actions,
    private val clockService: ClockService,
    private val careDetailsDestination: CareDetailsDestination,
    private val careDao: CareDao,
    private val careStepDao: CareStepDao
) {

    suspend operator fun invoke(activity: Activity): Either<Fail, Unit> {
        return either {
            val selectedSchemaWithSteps = selectCareSchema(activity).bind()
            val newCare = createNewCareFromSchema(selectedSchemaWithSteps)
            val newCareId = insertCareToDbAndGetId(newCare)
            val newCareSteps = createNewCareStepsFromSchema(selectedSchemaWithSteps, newCareId)
            insertCareStepsToDb(newCareSteps)
            openCareDetails(activity, newCareId)
        }
    }

    private suspend fun selectCareSchema(context: Context): Either<Fail, CareSchemaWithSteps> {
        val selectedSchema = actions.selectCareSchema(context)
        return if (selectedSchema == null) {
            Either.Left(Fail.CareSchemaNotSelected)
        } else {
            Either.Right(selectedSchema)
        }
    }

    private fun createNewCareFromSchema(schemaWithSteps: CareSchemaWithSteps): Care {
        return Care(
            schemaName = schemaWithSteps.careSchema.name,
            date = clockService.getNow(),
            notes = ""
        )
    }

    private suspend fun insertCareToDbAndGetId(care: Care): Long {
        return careDao.insert(care).first()
    }

    private fun createNewCareStepsFromSchema(
        schemaWithSteps: CareSchemaWithSteps,
        careId: Long
    ): List<CareStep> {
        return schemaWithSteps.steps.map {
            CareStep(
                productType = it.productType,
                order = it.order,
                productId = null,
                careId = careId
            )
        }
    }

    private suspend fun insertCareStepsToDb(careSteps: List<CareStep>) {
        careStepDao.insert(*careSteps.toTypedArray())
    }

    private fun openCareDetails(activity: Activity, careId: Long) {
        val params = CareDetailsDestination.Params(careId)
        careDetailsDestination.navigate(activity, params)
    }

    sealed class Fail {
        object CareSchemaNotSelected : Fail()
    }

    interface Actions {
        suspend fun selectCareSchema(context: Context): CareSchemaWithSteps?
    }
}