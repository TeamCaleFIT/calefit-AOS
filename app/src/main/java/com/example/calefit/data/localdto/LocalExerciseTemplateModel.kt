package com.example.calefit.data.localdto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calefit.data.ExerciseList
import kotlinx.serialization.Serializable


@Entity(tableName = "template")
@Serializable
data class LocalExerciseTemplateModel(

    @ColumnInfo(name = "Exercise")
    var list: List<ExerciseList.Exercise> = emptyList(),
    @PrimaryKey @ColumnInfo(name = "templateName")
    var templateName: String = ""
)