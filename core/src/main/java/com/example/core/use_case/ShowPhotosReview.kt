package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.Care
import com.example.core.domain.DayPhotos
import com.example.core.gateway.CareRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShowPhotosReview(
    private val careRepo: CareRepo
) : FlowUseCase<Unit, List<DayPhotos>>() {

    override fun execute(input: Unit): Flow<List<DayPhotos>> {
        return careRepo.findAll().map { list ->
            list.sortedByDescending { it.date }
                .filter { it.photos.isNotEmpty() }
                .groupBy { it.date }
                .map { DayPhotos(date = it.key, photos = it.value.allPhotos()) }
        }
    }

    private fun List<Care>.allPhotos(): List<String> {
        return flatMap { it.photos }
    }

}