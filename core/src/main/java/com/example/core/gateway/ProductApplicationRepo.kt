package com.example.core.gateway

import com.example.core.domain.ProductApplication
import kotlinx.coroutines.flow.Flow

interface ProductApplicationRepo {

    fun findAll(): Flow<List<ProductApplication>>
}