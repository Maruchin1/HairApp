package com.example.data.repo

import com.example.core.domain.Application
import com.example.core.gateway.ApplicationRepo
import com.example.data.dao.ApplicationDao
import com.example.data.entity.ApplicationEntity
import com.example.data.room.Mapper
import com.example.data.room.mapList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class RoomApplicationRepo(
    private val mapper: Mapper,
    private val applicationDao: ApplicationDao
) : ApplicationRepo {

    private val applications = arrayOf(
        ApplicationEntity("Łagodny szampon", Application.Type.SHAMPOO),
        ApplicationEntity("Średni szampon", Application.Type.SHAMPOO),
        ApplicationEntity("Mocny szampon", Application.Type.SHAMPOO),
        ApplicationEntity("Odżywka", Application.Type.CONDITIONER),
        ApplicationEntity("Krem", Application.Type.OTHER),
        ApplicationEntity("Maska", Application.Type.OTHER),
        ApplicationEntity("Odżywka b/s", Application.Type.OTHER),
        ApplicationEntity("Olej", Application.Type.OTHER),
        ApplicationEntity("Pianka", Application.Type.OTHER),
        ApplicationEntity("Serum", Application.Type.OTHER),
        ApplicationEntity("Żel", Application.Type.OTHER),
        ApplicationEntity("Inny", Application.Type.OTHER)
    )

    init {
        prepopulate()
    }

    override fun findAll(): Flow<List<Application>> {
        return applicationDao.findAll()
            .mapList { mapper.toDomain(it) }
    }

    private fun prepopulate() = GlobalScope.launch {
        val existing = applicationDao.findAll().first()
        if (existing.isEmpty()) {
            applicationDao.insert(*applications)
        }
    }

}