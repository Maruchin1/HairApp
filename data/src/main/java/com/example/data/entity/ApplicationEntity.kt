package com.example.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.core.domain.Application

@Entity
internal data class ApplicationEntity(

    @PrimaryKey
    val applicationName: String,

    val type: Application.Type
) {

    constructor(application: Application) : this(
        applicationName = application.name,
        type = application.type
    )
}