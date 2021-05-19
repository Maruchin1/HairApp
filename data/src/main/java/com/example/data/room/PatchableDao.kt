package com.example.data.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.data.entity.BaseEntity

internal interface PatchableDao<T> {

    @Insert
    suspend fun insert(entities: List<T>)

    @Update
    suspend fun update(entities: List<T>)

    @Delete
    suspend fun delete(entities: List<T>)
}

internal suspend fun <T : BaseEntity> PatchableDao<T>.patch(
    newData: List<T>,
    existingData: List<T>
) {
    updateEntities(newData, existingData)
    addEntities(newData, existingData)
    deleteEntities(newData, existingData)
}

private suspend fun <T : BaseEntity> PatchableDao<T>.updateEntities(
    newEntities: List<T>,
    existingEntities: List<T>
) {
    val toUpdate = newEntities.filter { new ->
        val existing = existingEntities.find { it.id == new.id }
        existing != null && existing.id != new.id
    }
    update(toUpdate)
}

private suspend fun <T : BaseEntity> PatchableDao<T>.addEntities(
    newEntities: List<T>,
    existingEntities: List<T>
) {
    val toAdd = newEntities.filter { new ->
        val existing = existingEntities.find { it.id == new.id }
        existing == null
    }
    insert(toAdd)
}

private suspend fun <T : BaseEntity> PatchableDao<T>.deleteEntities(
    newEntities: List<T>,
    existingEntities: List<T>
) {
    val toDelete = existingEntities.filter { existing ->
        val new = newEntities.find { it.id == existing.id }
        new == null
    }
    delete(toDelete)
}