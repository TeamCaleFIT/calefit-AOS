package com.example.calefit.data.localdto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity(tableName = "template")
@Serializable
data class LocalExerciseTemplateModel(

    @ColumnInfo(name = "title")
    var title: String = "",
    @PrimaryKey @ColumnInfo(name = "date")
    var date: String = ""
)