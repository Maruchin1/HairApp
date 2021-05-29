package com.example.cares_list.use_case

import android.app.Activity
import android.content.Context
import arrow.core.Either
import arrow.core.computations.either
import com.example.corev2.entities.CareSchema
import com.example.corev2.navigation.CareDetailsDestination

internal class OpenAddNewCareUseCase(
    private val actions: Actions,
    private val careDetailsDestination: CareDetailsDestination
) {

    suspend operator fun invoke(activity: Activity): Either<Fail, Unit> {
        return either {
            val selectedSchema = selectCareSchema(activity).bind()
            openNewCareWithSelectedSchema(activity, selectedSchema)
        }
    }

    private suspend fun selectCareSchema(context: Context): Either<Fail, CareSchema> {
        val selectedSchema = actions.selectCareSchema(context)
        return if (selectedSchema == null) {
            Either.Left(Fail.CareSchemaNotSelected)
        } else {
            Either.Right(selectedSchema)
        }
    }

    private fun openNewCareWithSelectedSchema(activity: Activity, selectedSchema: CareSchema) {
        val params = CareDetailsDestination.Params.AddNewCare(selectedSchema.id)
        careDetailsDestination.navigate(activity, params)
    }

    sealed class Fail {
        object CareSchemaNotSelected : Fail()
    }

    interface Actions {
        suspend fun selectCareSchema(context: Context): CareSchema?
    }
}