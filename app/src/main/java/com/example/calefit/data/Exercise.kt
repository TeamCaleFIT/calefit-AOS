package com.example.calefit.data

data class ExerciseList(
    val date: String = "",
    val name: String = "",
    val list: List<Exercise>,
) {
    data class Exercise(
        val id: String,
        val name: String,
        val cycleList: List<Cycle> = DEFAULT_CYCLE_LIST,
    )

    data class Cycle(
        val id: String,
        val cycle: Int = 0,
        val weight: Int = 0,
    )

    companion object {
        val DEFAULT_CYCLE_LIST = listOf(
            Cycle(
                id = "1",
                cycle = 0,
                weight = 0
            )
        )
    }
}