package com.example.room_database.repositories

import com.example.common.repository.CareSchemaRepo
import com.example.core.domain.CareSchema
import com.example.room_database.transactions.AddNewCareSchemaTransaction
import com.example.room_database.transactions.UpdateCareSchemaTransaction
import kotlinx.coroutines.flow.Flow

internal class RoomCareSchemaRepo(
    private val addNewCareSchemaTransaction: AddNewCareSchemaTransaction,
    private val updateCareSchemaTransaction: UpdateCareSchemaTransaction
) : CareSchemaRepo {

    override suspend fun addNew(careSchema: CareSchema): Int {
        return addNewCareSchemaTransaction(careSchema)
    }

    override suspend fun update(careSchema: CareSchema) {
        updateCareSchemaTransaction(careSchema)
    }

    override suspend fun delete(careSchema: CareSchema) {
        TODO("Not yet implemented")
    }

    override fun findById(id: Int): Flow<CareSchema?> {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flow<List<CareSchema>> {
        TODO("Not yet implemented")
    }
}