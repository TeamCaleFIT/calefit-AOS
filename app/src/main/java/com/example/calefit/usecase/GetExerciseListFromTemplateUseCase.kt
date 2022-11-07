package com.example.calefit.usecase

import com.example.calefit.data.ExerciseList
import com.example.calefit.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExerciseListFromTemplateUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(templateName: String): Flow<ExerciseList?> {
        return repository.getExerciseTemplateFromRoom().map {
            it[templateName]
        }
    }
}