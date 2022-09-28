package com.example.calefit.data.localdto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calefit.data.ExerciseList.Exercise
import kotlinx.serialization.Serializable

@Entity(tableName = "exercise")
@Serializable
data class LocalExerciseListModel(

    @ColumnInfo(name = "Exercise")
    var list: List<Exercise> = emptyList(),
    @PrimaryKey @ColumnInfo(name = "date")
    var date: String = ""
)