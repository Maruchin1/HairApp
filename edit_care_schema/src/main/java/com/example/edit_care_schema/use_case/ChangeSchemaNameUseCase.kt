package com.example.edit_care_schema.use_case

import android.content.Context
import arrow.core.Either
import arrow.core.computations.either
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.ui.DialogService

internal class ChangeSchemaNameUseCase(
    private val actions: Actions,
    private val careSchemaDao: CareSchemaDao,
) {

    suspend operator fun invoke(
        context: Context,
        careSchema: CareSchema?
    ): Either<Fail, Unit> {
        if (careSchema == null) {
            return Either.Left(Fail.NoCareSchema)
        }
        return either {
            val newName = askForNewName(context, careSchema.name).bind()
            val update = createUpdate(careSchema, newName).bind()
            careSchemaDao.update(update)
        }
    }

    private suspend fun askForNewName(
        context: Context,
        currentName: String
    ): Either<Fail, String> {
        val newName = actions.askForNewName(context, currentName)
        return if (newName == null) {
            Either.Left(Fail.NewNameNotTyped)
        } else {
            Either.Right(newName)
        }
    }

    private fun createUpdate(
        careSchema: CareSchema?,
        newName: String
    ): Either<Fail, CareSchema> {
        return if (careSchema == null) {
            Either.Left(Fail.NoCareSchema)
        } else {
            val update = careSchema.copy(name = newName)
            Either.Right(update)
        }
    }

    sealed class Fail {
        object NoCareSchema : Fail()
        object NewNameNotTyped : Fail()
    }

    interface Actions {
        suspend fun askForNewName(context: Context, currentName: String): String?
    }
}