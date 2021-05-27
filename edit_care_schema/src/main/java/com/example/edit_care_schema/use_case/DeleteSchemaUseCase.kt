package com.example.edit_care_schema.use_case

import android.content.Context
import arrow.core.Either
import arrow.core.computations.either
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.ui.DialogService
import com.example.edit_care_schema.R

internal class DeleteSchemaUseCase(
    private val dialogService: DialogService,
    private val careSchemaDao: CareSchemaDao
) {

    suspend operator fun invoke(context: Context, schema: CareSchema?): Either<Fail, Unit> {
        if (schema == null) {
            return Either.Left(Fail.NoCareSchema)
        }
        return either {
            confirmDeletion(context).bind()
            deleteSchemaFromDb(schema)
        }
    }

    private suspend fun confirmDeletion(context: Context): Either<Fail, Unit> {
        val confirmed = dialogService.confirm(
            context = context,
            title = context.getString(R.string.delete_care_schema)
        )
        return if (confirmed) {
            Either.Right(Unit)
        } else {
            Either.Left(Fail.NotConfirmed)
        }
    }

    private suspend fun deleteSchemaFromDb(schema: CareSchema) {
        careSchemaDao.delete(schema)
    }

    sealed class Fail {
        object NoCareSchema : Fail()
        object NotConfirmed : Fail()
    }
}