package com.example.calefit.data

import kotlinx.serialization.Serializable

data class ExerciseList(
    val date: String = "",
    var templateName: String = "",
    var list: List<Exercise>,
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
