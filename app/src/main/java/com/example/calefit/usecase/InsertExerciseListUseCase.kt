package com.example.calefit.usecase

import com.example.calefit.data.ExerciseList
import com.example.calefit.data.localdto.LocalExerciseListModel
import com.example.calefit.repository.ExerciseRepository
import javax.inject.Inject

class InsertExerciseListUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend fun save(exerciseList: ExerciseList, date: String) {
        repository.insertExerciseList(LocalExerciseListModel.from(exerciseList, date))
    }
}