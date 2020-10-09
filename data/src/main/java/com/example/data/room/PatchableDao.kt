package com.example.data.room

import androidx.room.Delete
import androidx.room.Insert

internal interface PatchableDao<T> {

    @Insert
    suspend fun insert(entities: List<T>)

    @Delete
    suspend fun delete(entities: List<T>)
}

internal suspend fun <T> PatchableDao<T>.patch(newData: List<T>, existingData: List<T>) {
    val toAdd = newData.filter { !existingData.contains(it) }
    val toDelete = existingData.filter { !newData.contains(it) }
    insert(toAdd)
    delete(toDelete)
}