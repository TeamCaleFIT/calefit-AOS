package com.example.calefit.data

import kotlinx.serialization.Serializable

data class ExerciseList(
    val date: String = "",
    var templateName: String = "",
    val list: List<Exercise>,
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
        val cycle: String,
        val weight: String,
    )

    companion object {
        val DEFAULT_CYCLE_LIST = listOf(
            Sets(
                id = "1",
                cycle = "",
                weight = ""
            )
        )
    }
}
