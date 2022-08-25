package com.example.calefit.repository

import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseList

interface ExerciseRepository {

    fun getExerciseListOrError(): Aggregate<HashMap<String, List<ExerciseList.Exercise>>>
}