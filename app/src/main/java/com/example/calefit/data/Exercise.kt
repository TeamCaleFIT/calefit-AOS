package com.example.calefit.data

data class ExerciseList(
    val date: String,
    val list: List<Exercise>,
) {
    data class Exercise(
        val id: String,
        val name: String,
        val cycle: String,
        val weight: String,
    )
}