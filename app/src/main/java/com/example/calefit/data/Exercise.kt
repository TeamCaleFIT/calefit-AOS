package com.example.calefit.data

import com.example.calefit.data.localdto.LocalExerciseListModel
import kotlinx.serialization.Serializable

data class ExerciseList(
    val date: String = "",
    var templateName: String = "",
    val list: List<Exercise> = emptyList(),
) : java.io.Serializable {

    @Serializable
    data class Exercise(
        val id: String,
        val name: String,
        val cycleList: List<Sets> = DEFAULT_CYCLE_LIST
    )

    @Serializable
    data class Sets(
        val id: String,
        val cycle: Int,
        val weight: Int,
    ) : java.io.Serializable

    fun toLocalModel() = LocalExerciseListModel(
        list = this.list,
        templateName = this.templateName,
        date = this.date
    )

    companion object {
        val DEFAULT_CYCLE_LIST = listOf(
            Sets(
                id = "1",
                cycle = 0,
                weight = 0
            )
        )
    }
}
