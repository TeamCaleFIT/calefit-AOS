package com.example.calefit.repository

import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseList

interface ExerciseRepository {

    fun getExerciseDataFromRepository(): Aggregate<HashMap<String, ExerciseList>>
}
