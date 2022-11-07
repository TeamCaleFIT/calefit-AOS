package com.example.calefit.data.localdto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calefit.data.ExerciseList
import com.example.calefit.data.ExerciseList.Exercise
import kotlinx.serialization.Serializable

@Entity(tableName = "exercise")
@Serializable
data class LocalExerciseListModel(

    @ColumnInfo(name = "exercise")
    var list: List<Exercise> = emptyList(),
    @ColumnInfo(name = "templateName")
    var templateName: String = "",
    @PrimaryKey @ColumnInfo(name = "date")
    var date: String = ""
) {
    companion object {
        fun from(exerciseList: ExerciseList, date: String) =
            LocalExerciseListModel(
                list = exerciseList.list,
                templateName = exerciseList.templateName,
                date = date
            )
    }
}