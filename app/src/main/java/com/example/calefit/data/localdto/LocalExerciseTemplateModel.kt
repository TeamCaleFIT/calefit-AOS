package com.example.calefit.data.localdto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calefit.data.ExerciseList
import kotlinx.serialization.Serializable


@Entity(tableName = "template")
@Serializable
data class LocalExerciseTemplateModel(

    @ColumnInfo(name = "exercise")
    var list: List<ExerciseList.Exercise> = emptyList(),
    @ColumnInfo(name = "date")
    var date: String = "",
    @PrimaryKey @ColumnInfo(name = "templateName")
    var templateName: String = ""
) {
    companion object {
        fun from(exerciseList: ExerciseList, date: String, templateName: String) =
            LocalExerciseTemplateModel(
                list = exerciseList.list,
                date = date,
                templateName = templateName
            )
    }
}