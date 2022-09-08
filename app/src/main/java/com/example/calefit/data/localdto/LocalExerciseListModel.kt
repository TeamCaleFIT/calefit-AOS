package com.example.calefit.data.localdto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calefit.data.ExerciseList.Exercise
import kotlinx.serialization.Serializable

@Entity(tableName = "dailyExercise")
@Serializable
data class LocalExerciseListModel(

    @ColumnInfo(name = "Exercise")
    var list: List<Exercise> = emptyList(),
    @ColumnInfo(name = "templateName")
    var name: String = "",
    @PrimaryKey @ColumnInfo(name = "date")
    var date: String = ""
)