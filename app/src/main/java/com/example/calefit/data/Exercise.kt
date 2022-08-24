package com.example.calefit.data

data class ExerciseList(
    val date: String = "",
    var templateName: String = "",
    val list: List<Exercise>,
) {
    data class Exercise(
        val id: String,
        val name: String,
        val cycleList: List<Cycle> = DEFAULT_CYCLE_LIST
    )

    data class Cycle(
        val id: String,
        val cycle: String,
        val weight: String,
    )

    companion object {
        val DEFAULT_CYCLE_LIST = listOf(
            Cycle(
                id = "1",
                cycle = "",
                weight = ""
            )
        )
    }
}