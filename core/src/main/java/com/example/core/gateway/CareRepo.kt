package com.example.core.gateway

import com.example.core.domain.Care

interface CareRepo {

    suspend fun add(care: Care)

    suspend fun existsById(careId: Int): Boolean
}