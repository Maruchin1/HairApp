package com.example.room_database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.core.domain.CareStep

@Entity(
    tableName = "CareSchemaStep",
    foreignKeys = [
        ForeignKey(
            entity = CareSchemaEntity::class,
            parentColumns = ["id"],
            childColumns = ["careSchemaId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
internal data class CareSchemaStepEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = -1,

    @ColumnInfo(name = "type")
    var type: CareStep.Type = CareStep.Type.OTHER,

    @ColumnInfo(name = "order")
    var order: Int = -1,

    @ColumnInfo(name = "careSchemaId")
    var careSchemaId: Int = -1
)